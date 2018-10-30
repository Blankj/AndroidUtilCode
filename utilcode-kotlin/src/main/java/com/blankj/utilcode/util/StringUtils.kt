@file:JvmName("StringUtils")

package com.blankj.utilcode.util

/**
 * Return whether the string is null or 0-length.
 *
 * @param s The string.
 * @return `true`: yes<br></br> `false`: no
 */
fun isEmpty(s: CharSequence?): Boolean {
    return s == null || s.isEmpty()
}

/**
 * Return whether the string is null or whitespace.
 *
 * @param s The string.
 * @return `true`: yes<br></br> `false`: no
 */
fun isTrimEmpty(s: String?): Boolean {
    return s == null || s.trim { it <= ' ' }.isEmpty()
}

/**
 * Return whether the string is null or white space.
 *
 * @param s The string.
 * @return `true`: yes<br></br> `false`: no
 */
fun isSpace(s: String?): Boolean {
    if (s == null) return true
    var i = 0
    val len = s.length
    while (i < len) {
        if (!Character.isWhitespace(s[i])) {
            return false
        }
        ++i
    }
    return true
}

/**
 * Return whether string1 is equals to string2.
 *
 * @param s1 The first string.
 * @param s2 The second string.
 * @return `true`: yes<br></br>`false`: no
 */
fun equals(s1: CharSequence?, s2: CharSequence?): Boolean {
    if (s1 === s2) return true
    if (s1 != null && s2 != null) {
        val length: Int = s1.length
        if (length == s2.length) {
            if (s1 is String && s2 is String) {
                return s1 == s2
            } else {
                for (i in 0 until length) {
                    if (s1[i] != s2[i]) return false
                }
                return true
            }
        }
    }
    return false
}

/**
 * Return whether string1 is equals to string2, ignoring case considerations..
 *
 * @param s1 The first string.
 * @param s2 The second string.
 * @return `true`: yes<br></br>`false`: no
 */
fun equalsIgnoreCase(s1: String?, s2: String?): Boolean {
    return s1.equals(s2, ignoreCase = true)
}

/**
 * Return `""` if string equals null.
 *
 * @param s The string.
 * @return `""` if string equals null
 */
fun null2Length0(s: String?): String {
    return s ?: ""
}

/**
 * Return the length of string.
 *
 * @param s The string.
 * @return the length of string
 */
fun length(s: CharSequence?): Int {
    return s?.length ?: 0
}

/**
 * Set the first letter of string upper.
 *
 * @param s The string.
 * @return the string with first letter upper.
 */
fun upperFirstLetter(s: String?): String {
    if (s == null || s.isEmpty()) return ""
    return if (!Character.isLowerCase(s[0])) s else (s[0].toInt() - 32).toChar().toString() + s.substring(1)
}

/**
 * Set the first letter of string lower.
 *
 * @param s The string.
 * @return the string with first letter lower.
 */
fun lowerFirstLetter(s: String?): String {
    if (s == null || s.isEmpty()) return ""
    return if (!Character.isUpperCase(s[0])) s else (s[0].toInt() + 32).toChar().toString() + s.substring(1)
}

/**
 * Reverse the string.
 *
 * @param s The string.
 * @return the reverse string.
 */
fun reverse(s: String?): String {
    if (s == null) return ""
    val len = s.length
    if (len <= 1) return s
    val mid = len shr 1
    val chars = s.toCharArray()
    var c: Char
    for (i in 0 until mid) {
        c = chars[i]
        chars[i] = chars[len - i - 1]
        chars[len - i - 1] = c
    }
    return String(chars)
}

/**
 * Convert string to DBC.
 *
 * @param s The string.
 * @return the DBC string
 */
fun toDBC(s: String?): String {
    if (s == null || s.isEmpty()) return ""
    val chars = s.toCharArray()
    var i = 0
    val len = chars.size
    while (i < len) {
        when {
            chars[i].toInt() == 12288 -> chars[i] = ' '
            chars[i].toInt() in 65281..65374 -> chars[i] = (chars[i].toInt() - 65248).toChar()
            else -> chars[i] = chars[i]
        }
        i++
    }
    return String(chars)
}

/**
 * Convert string to SBC.
 *
 * @param s The string.
 * @return the SBC string
 */
fun toSBC(s: String?): String {
    if (s == null || s.isEmpty()) return ""
    val chars = s.toCharArray()
    var i = 0
    val len = chars.size
    while (i < len) {
        when {
            chars[i] == ' ' -> chars[i] = 12288.toChar()
            chars[i].toInt() in 33..126 -> chars[i] = (chars[i].toInt() + 65248).toChar()
            else -> chars[i] = chars[i]
        }
        i++
    }
    return String(chars)
}