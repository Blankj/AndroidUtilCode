package com.blankj.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project

import static com.blankj.plugin.FormatUtils.LINE_SEP;

class ReadmeSubPlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {
        project.extensions.create('readme', ReadmeExtension)

        project.task('readmeTask') {
            doLast {
                println "readmeTask start..."

                def readmeCN = project['readme'].readmeCnFile
                def readmeEng = project['readme'].readmeFile
                readmeOfSubUtil2Eng(readmeCN, readmeEng)

                println "readmeTask finished."
            }
        }
    }

    static def readmeOfSubUtil2Eng(File readmeCN, File readmeEng) {
        FormatUtils.format(readmeCN)
        def lines = readmeCN.readLines("UTF-8"),
            sb = new StringBuilder("## How to use" + LINE_SEP
                    + LINE_SEP +
                    "You should copy the following classes which you want to use in your project." + LINE_SEP),
            i = 3,
            size = lines.size()
        for (; i < size; ++i) {
            String line = lines.get(i)
            if (line.contains("* ###")) {
                String utilsName = line.substring(line.indexOf("[") + 1, line.indexOf("Utils"))
                sb.append("* ### About ").append(utilsName).append(line.substring(line.indexOf(" -> ")))
            } else if (line.contains(": ") && !line.contains("[")) {
                sb.append(line.substring(0, line.indexOf(':')).trim())
            } else {
                sb.append(line)
            }
            sb.append(LINE_SEP)
        }
        readmeEng.write(sb.toString(), "UTF-8")
    }
}
