/**
 * <pre>
 *     author: blankj
 *     blog  : http://blankj.com
 *     time  : 2019/07/13
 *     desc  :
 * </pre>
 */
class GLog {

    def static debugSwitch = true

    static d(Object... contents) {
        if (!debugSwitch) return contents
        return l(contents)
    }

    static l(Object... contents) {
        StringBuilder sb = new StringBuilder()
        sb.append(LogConst.BORDER_TOP)
        sb.append(borderMsg(processContents(contents)))
        sb.append(LogConst.BORDER_BTM)
        print sb.toString()
        return contents
    }

    private static borderMsg(String msg) {
        StringBuilder sb = new StringBuilder()
        object2String(msg).split(LogConst.LINE_SEP).each { line ->
            sb.append(LogConst.BORDER_LFT).append(line).append(LogConst.LINE_SEP)
        }
        return sb
    }

    private static processContents(final Object... contents) {
        String body = LogConst.NULL
        if (contents != null) {
            if (contents.length == 1) {
                body = object2String(contents[0])
            } else {
                StringBuilder sb = new StringBuilder()
                int i = 0
                for (int len = contents.length; i < len; ++i) {
                    Object content = contents[i]
                    sb.append("args[$i] = ")
                            .append(object2String(content))
                            .append(LogConst.LINE_SEP)
                }
                body = sb.toString()
            }
        }
        return body.length() == 0 ? LogConst.NOTHING : body
    }

    static String object2String(Object object) {
        if (object == null) return "null";
        if (object.getClass().isArray()) return LogFormatter.array2String(object);
        if (object instanceof List) return LogFormatter.list2String(object);
        if (object instanceof Map) return LogFormatter.map2String(object);
        if (object instanceof Throwable) return LogFormatter.throwable2String(object);
        return object.toString();
    }

    static class LogFormatter {

        private static array2String(Object object) {
            if (object instanceof Object[]) {
                return Arrays.deepToString((Object[]) object);
            } else if (object instanceof boolean[]) {
                return Arrays.toString((boolean[]) object);
            } else if (object instanceof byte[]) {
                return Arrays.toString((byte[]) object);
            } else if (object instanceof char[]) {
                return Arrays.toString((char[]) object);
            } else if (object instanceof double[]) {
                return Arrays.toString((double[]) object);
            } else if (object instanceof float[]) {
                return Arrays.toString((float[]) object);
            } else if (object instanceof int[]) {
                return Arrays.toString((int[]) object);
            } else if (object instanceof long[]) {
                return Arrays.toString((long[]) object);
            } else if (object instanceof short[]) {
                return Arrays.toString((short[]) object);
            }
            throw new IllegalArgumentException("Array has incompatible type: " + object.getClass());
        }

        private static list2String(List list) {
            StringBuilder sb = new StringBuilder()
            sb.append("[")
            list.each { v ->
                if (v instanceof Map || v instanceof List) {
                    sb.append(String.format("$LogConst.LINE_SEP%${deep++ * 8}s${object2String(v)},", ""))
                    deep--
                } else {
                    sb.append(String.format("$LogConst.LINE_SEP%${deep * 8}s$v,", ""))
                }
            }
            sb.deleteCharAt(sb.length() - 1)
            if (deep - 1 == 0) {
                sb.append("$LogConst.LINE_SEP]")
            } else {
                sb.append(String.format("$LogConst.LINE_SEP%${(deep - 1) * 8}s]", ""))
            }
            return sb.toString()
        }

        private static deep = 1;

        private static map2String(Map map) {
            StringBuilder sb = new StringBuilder()
            sb.append("[")
            map.each { k, v ->
                if (v instanceof Map || v instanceof List) {
                    sb.append(String.format("$LogConst.LINE_SEP%${deep++ * 8}s%-26s: ${object2String(v)},", "", k))
                    deep--
                } else {
                    sb.append(String.format("$LogConst.LINE_SEP%${deep * 8}s%-26s: $v,", "", k))
                }
            }
            sb.deleteCharAt(sb.length() - 1)
            if (deep - 1 == 0) {
                sb.append("$LogConst.LINE_SEP]")
            } else {
                sb.append(String.format("$LogConst.LINE_SEP%${(deep - 1) * 8}s]", ""))
            }
            return sb.toString()
        }

        private static throwable2String(Throwable throwable) {
            final List<Throwable> throwableList = new ArrayList<>();
            while (throwable != null && !throwableList.contains(throwable)) {
                throwableList.add(throwable);
                throwable = throwable.getCause();
            }
            final int size = throwableList.size();
            final List<String> frames = new ArrayList<>();
            List<String> nextTrace = getStackFrameList(throwableList.get(size - 1));
            for (int i = size; --i >= 0;) {
                final List<String> trace = nextTrace;
                if (i != 0) {
                    nextTrace = getStackFrameList(throwableList.get(i - 1));
                    removeCommonFrames(trace, nextTrace);
                }
                if (i == size - 1) {
                    frames.add(throwableList.get(i).toString());
                } else {
                    frames.add(" Caused by: " + throwableList.get(i).toString());
                }
                frames.addAll(trace);
            }
            StringBuilder sb = new StringBuilder();
            for (final String element : frames) {
                sb.append(element).append(LogConst.LINE_SEP);
            }
            return sb.toString();
        }

        private static List<String> getStackFrameList(final Throwable throwable) {
            final StringWriter sw = new StringWriter();
            final PrintWriter pw = new PrintWriter(sw, true);
            throwable.printStackTrace(pw);
            final String stackTrace = sw.toString();
            final StringTokenizer frames = new StringTokenizer(stackTrace, LogConst.LINE_SEP);
            final List<String> list = new ArrayList<>();
            boolean traceStarted = false;
            while (frames.hasMoreTokens()) {
                final String token = frames.nextToken();
                // Determine if the line starts with <whitespace>at
                final int at = token.indexOf("at");
                if (at != -1 && token.substring(0, at).trim().isEmpty()) {
                    traceStarted = true;
                    list.add(token);
                } else if (traceStarted) {
                    break;
                }
            }
            return list;
        }

        private static void removeCommonFrames(final List<String> causeFrames, final List<String> wrapperFrames) {
            int causeFrameIndex = causeFrames.size() - 1;
            int wrapperFrameIndex = wrapperFrames.size() - 1;
            while (causeFrameIndex >= 0 && wrapperFrameIndex >= 0) {
                // Remove the frame from the cause trace if it is the same
                // as in the wrapper trace
                final String causeFrame = causeFrames.get(causeFrameIndex);
                final String wrapperFrame = wrapperFrames.get(wrapperFrameIndex);
                if (causeFrame.equals(wrapperFrame)) {
                    causeFrames.remove(causeFrameIndex);
                }
                causeFrameIndex--;
                wrapperFrameIndex--;
            }
        }
    }

    static class LogConst {
        static LINE_SEP = System.getProperty("line.separator");
        static BORDER_TOP = "┌────────────────────────────────────────────────────────────────────────────────────────────────────────────────" + LINE_SEP
        static BORDER_LFT = "│ ";
        static BORDER_BTM = "└────────────────────────────────────────────────────────────────────────────────────────────────────────────────" + LINE_SEP

        static final NOTHING = "log nothing";
        static final NULL = "null";
    }
}
