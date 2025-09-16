package com.sheypoor.sobhan.materialshowcaseview


interface IDetachedListener {
    fun onShowcaseDetached(
        showcaseView: MaterialShowcaseView,
        wasDismissed: Boolean,
        wasSkipped: Boolean
    )
}
