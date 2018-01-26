package com.codefororlando.petadoption.helper

class ContactFormatter {

    companion object {

        fun formatPhoneNumber(unformattedNumber: String): String {
            val numbersOnly = unformattedNumber.replace("[^0-9]".toRegex(), "")
            return "tel:" + numbersOnly
        }

        fun formatWebLink(link: String): String {
            var url = link
            if (!url.startsWith("http")) {
                url = "http://" + url
            }
            return url
        }
    }
}