package net.lsafer.edgeseek.app.util

fun langIsRTL(languageTag: String): Boolean {
    val language = languageTag
        .splitToSequence('_', '-')
        .firstOrNull()

    return when (language) {
        "ae", /* Avestan */
        "ar", /* 'العربية', Arabic */
        "arc",  /* Aramaic */
        "bcc",  /* 'بلوچی مکرانی', Southern Balochi */
        "bqi",  /* 'بختياري', Bakthiari */
        "ckb",  /* 'Soranî / کوردی', Sorani */
        "dv",   /* Dhivehi */
        "fa",   /* 'فارسی', Persian */
        "glk",  /* 'گیلکی', Gilaki */
        "ku",   /* 'Kurdî / كوردی', Kurdish */
        "mzn",  /* 'مازِرونی', Mazanderani */
        "nqo",  /* N'Ko */
        "pnb",  /* 'پنجابی', Western Punjabi */
        "prs",  /* 'دری', Darī */
        "ps",   /* 'پښتو', Pashto, */
        "sd",   /* 'سنڌي', Sindhi */
        "ug",   /* 'Uyghurche / ئۇيغۇرچە', Uyghur */
        "ur",    /* 'اردو', Urdu */
        -> true

        else -> false
    }
}

@Deprecated("default is too ambiguous")
fun langSelect(languages: Collection<String>, ranges: List<String>, default: String): String {
    return langSelect(languages, ranges) ?: default
}

/**
 * @param languages the available languages
 * @param ranges the requested languages
 */
fun langSelect(languages: Collection<String>, ranges: List<String>): String? {
    if (ranges.isEmpty() || languages.isEmpty())
        return null

    for (range in ranges) {
        // Special language range ("*") is ignored in lookup.
        if (range == "*")
            continue

        var normalRange = range.lowercase()

        while (normalRange.isNotEmpty()) {
            val regex = normalRange
                .replace("*", "[a-z0-9]*")
                .toRegex()

            for (language in languages) {
                val normalLanguage = language.lowercase()

                if (normalLanguage.matches(regex))
                    return language
            }

            // Truncate from the end....
            normalRange = truncateRange(normalRange)
        }
    }

    return null
}

/* Truncate the range from end during the lookup match; copy-paste from java LocaleMatcher.truncateRange */
private fun truncateRange(rangeForRegex: String): String {
    var rangeForRegexVar = rangeForRegex
    var index = rangeForRegexVar.lastIndexOf('-')
    if (index >= 0) {
        rangeForRegexVar = rangeForRegexVar.substring(0, index)

        // if range ends with an extension key, truncate it.
        index = rangeForRegexVar.lastIndexOf('-')
        if (index >= 0 && index == rangeForRegexVar.length - 2) {
            rangeForRegexVar = rangeForRegexVar.substring(0, rangeForRegexVar.length - 2)
        }
    } else {
        rangeForRegexVar = ""
    }
    return rangeForRegexVar
}
