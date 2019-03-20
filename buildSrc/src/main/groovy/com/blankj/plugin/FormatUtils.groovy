package com.blankj.plugin

class FormatUtils {

    static def LINE_SEP = System.getProperty("line.separator")
    static def LONG_SPACE = "                                        "

    static def format(File readmeCN) {
        def sb = new StringBuilder(),
            lines = readmeCN.readLines("UTF-8"),
            i = 0,
            size = lines.size()
        for (; i < size; ++i) {
            String line = lines.get(i)
            if (line.contains("* ###")) {
                sb.append(line).append(LINE_SEP)
                        .append("```").append(LINE_SEP)
                def maxLen = 0
                line = lines.get(i += 2)
                // get the max length of space
                for (def j = i; !line.equals("```"); line = lines.get(++j)) {
                    maxLen = Math.max(maxLen, line.replace(" ", "").replace(",", ", ").indexOf(':'))
                }
                line = lines.get(i)
                for (; !line.equals("```"); line = lines.get(++i)) {
                    def noSpaceLine = line.replace(" ", "")
                    def spaceLen = maxLen - line.replace(" ", "").replace(",", ", ").indexOf(':')
                    sb.append(noSpaceLine.substring(0, noSpaceLine.indexOf(':')).replace(",", ", "))
                            .append(LONG_SPACE.substring(0, spaceLen))// add the space
                            .append(': ')
                            .append(line.substring(line.indexOf(':') + 1).trim())
                            .append(LINE_SEP)
                }
                sb.append("```")
            } else {
                sb.append(line)
            }
            sb.append(LINE_SEP)
        }
        readmeCN.write(sb.toString(), "UTF-8")
    }
}
