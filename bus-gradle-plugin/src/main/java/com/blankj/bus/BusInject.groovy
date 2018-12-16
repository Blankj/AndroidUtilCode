package com.blankj.bus

import com.blankj.util.JavassistUtils
import com.blankj.util.ZipUtils
import javassist.CtClass
import javassist.CtMethod
import javassist.NotFoundException
import org.apache.commons.io.FileUtils

class BusInject {

    static void start(HashMap<String, String> bus, File busJar) {
        String jarPath = busJar.getAbsolutePath()
        String decompressedJarPath = jarPath.substring(0, jarPath.length() - 4);
        File decompressedJar = new File(decompressedJarPath)
        ZipUtils.unzipFile(busJar, decompressedJar)

        CtClass busUtils = JavassistUtils.getPool().get(Config.BUS_UTILS_CLASS)
        CtMethod callMethod;
        try {
            callMethod = busUtils.getDeclaredMethod("injectShell");
            callMethod.insertAfter(getInsertContent(bus, false));
        } catch (NotFoundException ignore) {
            callMethod = busUtils.getDeclaredMethod("post");
            callMethod.insertAfter(getInsertContent(bus, true));
        }
        busUtils.writeFile(decompressedJarPath)
        busUtils.defrost()
        FileUtils.forceDelete(busJar)
        ZipUtils.zipFiles(Arrays.asList(decompressedJar.listFiles()), busJar)
        FileUtils.forceDelete(decompressedJar)
    }

    private static String getInsertContent(HashMap<String, String> bus, boolean isLow) {
        StringBuilder sb = new StringBuilder();
        bus.each { String key, String val ->
            String name = key
            String[] method = val.split(' ')
            String returnType = method[0]
            String methodName = method[1]

            sb.append('if ("').append(name).append('".equals($1)) {\n')

            int st = methodName.indexOf('(')
            int end = methodName.length()
            String params = methodName.substring(st + 1, end - 1);
            if (params != '') {
                String[] paramArr = params.split(",")

                StringBuilder args = new StringBuilder()
                for (int i = 0; i < paramArr.length; i++) {
                    if (paramArr[i] == 'char') {
                        args.append(',$2[').append(i).append('].toString().charAt(0)')
                    } else if (paramArr[i] == 'boolean') {
                        args.append(',Boolean.parseBoolean($2[').append(i).append('].toString())')
                    } else if (paramArr[i] == 'byte') {
                        args.append(',Byte.parseByte($2[').append(i).append('].toString())')
                    } else if (paramArr[i] == 'short') {
                        args.append(',Short.parseShort($2[').append(i).append('].toString())')
                    } else if (paramArr[i] == 'int') {
                        args.append(',Integer.parseInt($2[').append(i).append('].toString())')
                    } else if (paramArr[i] == 'long') {
                        args.append(',Long.parseLong($2[').append(i).append('].toString())')
                    } else if (paramArr[i] == 'float') {
                        args.append(',Float.parseFloat($2[').append(i).append('].toString())')
                    } else if (paramArr[i] == 'double') {
                        args.append(',Double.parseDouble($2[').append(i).append('].toString())')
                    } else {
                        args.append(',(').append(paramArr[i]).append(')$2[').append(i).append(']')
                    }
                }
                methodName = methodName.substring(0, st + 1) + args.substring(1) + ")"
            }

            if (returnType.equals('void')) {
                sb.append(methodName).append(';\n').append('return null;\n')
            } else {
                sb.append('return ($w)').append(methodName).append(';\n')
            }
            sb.append("}")
        }
        if (isLow) {
            sb.append('android.util.Log.e("BusUtils", "bus of <" + $1 + "> didn\'t exist.");')
        }
        return sb.toString()
    }
}