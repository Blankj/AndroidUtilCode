package com.blankj.bus;


/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2018/09/21
 *     desc  :
 * </pre>
 */
public class BusTest {

//    @Test
//    public void test() throws Exception {
//        String rootPath = "/Users/blankj/Repo/AndroidUtilCode/app/build/intermediates/transforms/busTransform/debug/43/";
//        File root = new File(rootPath);
//        ClassPool mPool = new ClassPool(null);
//        mPool.appendSystemPath();
//        mPool.appendClassPath(rootPath);
//        mPool.appendClassPath("/Users/blankj/Library/Android/sdk/platforms/android-27/android.jar");
//        HashMap<String, String> busMap = new HashMap<>();
//        if (root.isDirectory()) {
//            Collection<File> files = FileUtils.listFiles(root, new String[]{"class"}, true);
//
//            for (File file : files) {
//                String fileName = file.getName();
//
//                String filePath = file.getCanonicalPath();
//                String packagePath = filePath.replace(rootPath, "");
//                String className = packagePath.replace(System.getProperty("file.separator"), ".");
//                // delete .class
//                className = className.substring(0, className.length() - 6);
//
//                CtClass ctClass = mPool.get(className);
//
//                CtMethod[] methods = ctClass.getDeclaredMethods();
//                for (CtMethod method : methods) {
//                    if (method.hasAnnotation(BusUtils.Subscribe.class)) {
//                        String name = ((BusUtils.Subscribe) method.getAnnotation(BusUtils.Subscribe.class)).name();
//                        if (busMap.containsKey(name)) {
//                            System.out.println("bus of " + name + " has registered." + method.getLongName());
//                            continue;
//                        }
//                        String longMethodName = method.getLongName();
//                        if (Modifier.isStatic(method.getModifiers())) {
//                            String sign = method.getReturnType().getName() + ' ' + longMethodName;
//                            busMap.put(name, sign);
//                        } else {// may be is kotlin
//                            processKt(mPool, busMap, method, name, longMethodName);
//                        }
//                    }
//                }
//            }
//            System.out.println(JsonUtils.getFormatJson(busMap));
//        }
//        CtClass ctClass = mPool.makeClass("com.blankj.bus.BusUtils");
//
//        String src = "" +
//                "public static Object post(String name, Object[] objects) {\n" +
//                "    if (name == null || name.length() == 0) return null;\n" +
//                "    return null;\n" +
//                "}";
//
//        CtMethod make = CtNewMethod.make(src, ctClass);
//        ctClass.addMethod(make);
//        make.insertAfter(getInsertContent(busMap));
//        ctClass.debugWriteFile();
//
//    }
//
//    private void processKt(ClassPool mPool,
//                           HashMap<String, String> busMap,
//                           CtMethod method, String name,
//                           String longMethodName) throws NotFoundException {
//        CtClass innerClass = method.getDeclaringClass();
//        try {
//            CtField instance = innerClass.getField("INSTANCE");
//            System.out.println("find INSTANCE: " + name + ": " + longMethodName);
//            int i = longMethodName.lastIndexOf('(');
//            String temp = longMethodName.substring(0, i);
//            int j = temp.lastIndexOf('.');
//            String sign = method.getReturnType().getName() + ' '
//                    + longMethodName.substring(0, j)
//                    + ".INSTANCE"
//                    + longMethodName.substring(j);
//            System.out.println(sign);
//            busMap.put(name, sign);
//        } catch (NotFoundException ignore) {
//            String innerClassSimpleName = innerClass.getSimpleName();
//            if (innerClassSimpleName.contains("$") && !innerClassSimpleName.endsWith("$")) {
//                String innerClassName = innerClass.getName();
//                String outerClassName = innerClassName.substring(0, innerClassName.lastIndexOf('$'));
//                CtClass outerClass = mPool.get(outerClassName);
//                try {
//                    CtField ctField = outerClass.getField(innerClassSimpleName.substring(innerClassSimpleName.lastIndexOf('$') + 1));
//                    String fieldName = ctField.getName();
//                    String methodName = longMethodName.replace("$" + fieldName, "." + fieldName);
//                    String sign = method.getReturnType().getName() + ' ' + methodName;
//                    busMap.put(name, sign);
//                } catch (NotFoundException e) {
//                    System.out.println(longMethodName + "is not static");
//                }
//            } else {
//                System.out.println(longMethodName + "is not static");
//            }
//        }
//    }
//
//    private static String getInsertContent(HashMap<String, String> bus) {
//        final StringBuilder sb = new StringBuilder();
//        bus.forEach(new BiConsumer<String, String>() {
//            @Override
//            public void accept(String name, String sign) {
//                String[] method = sign.split(" ");
//                String returnType = method[0];
//                String methodName = method[1];
//
//                sb.append("if (\"").append(name).append("\".equals($1)) {\n");
//
//                int st = methodName.indexOf('(');
//                int end = methodName.length();
//                String params = methodName.substring(st + 1, end - 1);
//                if (!params.equals("")) {
//                    String[] paramArr = params.split(",");
//
//                    StringBuilder args = new StringBuilder();
//                    for (int i = 0; i < paramArr.length; i++) {
//                        if (paramArr[i].equals("char")) {
//                            args.append(",$2[").append(i).append("].toString().charAt(0)");
//                        } else if (paramArr[i].equals("boolean")) {
//                            args.append(",Boolean.parseBoolean($2[").append(i).append("].toString())");
//                        } else if (paramArr[i].equals("byte")) {
//                            args.append(",Byte.parseByte($2[").append(i).append("].toString())");
//                        } else if (paramArr[i].equals("short")) {
//                            args.append(",Short.parseShort($2[").append(i).append("].toString())");
//                        } else if (paramArr[i].equals("int")) {
//                            args.append(",Integer.parseInt($2[").append(i).append("].toString())");
//                        } else if (paramArr[i].equals("long")) {
//                            args.append(",Long.parseLong($2[").append(i).append("].toString())");
//                        } else if (paramArr[i].equals("float")) {
//                            args.append(",Float.parseFloat($2[").append(i).append("].toString())");
//                        } else if (paramArr[i].equals("double")) {
//                            args.append(",Double.parseDouble($2[").append(i).append("].toString())");
//                        } else {
//                            args.append(",(").append(paramArr[i]).append(")$2[").append(i).append("]");
//                        }
//                    }
//                    methodName = methodName.substring(0, st + 1) + args.substring(1) + ")";
//                }
//
//                if (returnType.equals("void")) {
//                    sb.append(methodName).append(";\n").append("return null;\n");
//                } else {
//                    sb.append("return ($w)").append(methodName).append(";\n");
//                }
//                sb.append("}");
//            }
//        });
//        return sb.toString();
//    }
}
