package uk.co.deanwild.materialshowcaseview

import android.content.Context

class PrefsManager(private var context: Context?, showcaseID: String?) {
    private var showcaseID: String? = null

    init {
        this.showcaseID = showcaseID
    }


    /***
     * METHODS FOR INDIVIDUAL SHOWCASE VIEWS
     */
    fun hasFired(): Boolean {
        val status = this.sequenceStatus
        return (status == SEQUENCE_FINISHED)
    }

    fun setFired() {
        this.sequenceStatus = SEQUENCE_FINISHED
    }

    var sequenceStatus: Int
        /***
         * METHODS FOR SHOWCASE SEQUENCES
         */
        get() = context!!
            .getSharedPreferences(
                PREFS_NAME,
                Context.MODE_PRIVATE
            )
            .getInt(
                STATUS + showcaseID,
                SEQUENCE_NEVER_STARTED
            )
        set(status) {
            val internal = context!!.getSharedPreferences(
                PREFS_NAME,
                Context.MODE_PRIVATE
            )
            internal.edit().putInt(STATUS + showcaseID, status).apply()
        }


    fun resetShowcase() {
        Companion.resetShowcase(context!!, showcaseID)
    }

    fun close() {
        context = null
    }

    companion object {
        var SEQUENCE_NEVER_STARTED: Int = 0
        var SEQUENCE_FINISHED: Int = -1


        private const val PREFS_NAME = "material_showcaseview_prefs"
        private const val STATUS = "status_"
        fun resetShowcase(context: Context, showcaseID: String?) {
            val internal = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            internal.edit().putInt(STATUS + showcaseID, SEQUENCE_NEVER_STARTED).apply()
        }

        fun resetAll(context: Context) {
            val internal = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            internal.edit().clear().apply()
        }
    }
}
