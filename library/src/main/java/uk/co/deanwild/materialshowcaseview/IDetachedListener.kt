package uk.co.deanwild.materialshowcaseview


interface IDetachedListener {
    fun onShowcaseDetached(
        showcaseView: MaterialShowcaseView,
        wasDismissed: Boolean,
        wasSkipped: Boolean
    )
}
