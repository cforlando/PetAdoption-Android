package com.codefororlando.petadoption.helper

import android.util.Patterns
import java.util.regex.Pattern


class ContactParser {
    companion object {
        const val EMPTY = "EMPTY"

        private val PHONE_PATTERN = Pattern.compile("(\\+[0-9]+[\\- \\.]*)?" + "(\\([0-9]+\\)[\\- \\.]*)?" + "([0-9][0-9\\- \\.]+[0-9])")

        fun findEmail(text: String): String {
            return parse(Patterns.EMAIL_ADDRESS, text)
        }

        fun findPhoneNumber(text: String): String {
            return parse(PHONE_PATTERN, text)
        }

        fun findWeblink(text: String): String {
            return parse(Patterns.WEB_URL, text)
        }

        fun parse(pattern: Pattern, text: String): String {
            val matcher = pattern.matcher(text)
            while (matcher.find()) {
                return matcher.group()
            }
            return EMPTY
        }
    }
}