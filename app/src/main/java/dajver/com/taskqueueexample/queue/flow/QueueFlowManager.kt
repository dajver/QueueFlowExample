package dajver.com.taskqueueexample.queue.flow

import android.util.Log
import dajver.com.taskqueueexample.models.MetaDataModel
import dajver.com.taskqueueexample.queue.base.interfaces.BaseWorkListener
import dajver.com.taskqueueexample.queue.base.model.BaseWorkItem
import dajver.com.taskqueueexample.queue.enums.Statuses.CLEAN
import dajver.com.taskqueueexample.queue.enums.Statuses.CREATE
import dajver.com.taskqueueexample.queue.enums.Statuses.WORK
import dajver.com.taskqueueexample.queue.flow.clean.CleanProcess
import dajver.com.taskqueueexample.queue.flow.clean.CleanWorkItem
import dajver.com.taskqueueexample.queue.flow.create.CreateWorkItem
import dajver.com.taskqueueexample.queue.flow.work.WorkProcess
import dajver.com.taskqueueexample.queue.flow.work.WorkWorkItem
import dajver.com.taskqueueexample.queue.flow.create.CreateProcess as CreateProcess1

class QueueFlowManager(var queueFlowListener: QueueFlowListener) : BaseWorkListener {

    private var createProcess: CreateProcess1? = null
    private var workProcess: WorkProcess? = null
    private var cleanProcess: CleanProcess? = null
    private var metaDataModel: MetaDataModel? = null

    init {
        createProcess = CreateProcess1(this)
        workProcess = WorkProcess(this)
        cleanProcess = CleanProcess(this)

        metaDataModel = MetaDataModel(1234)

        makeSequentialStatusMap()
    }

    private fun makeSequentialStatusMap() {
        val statusMap = ArrayList<String>()
        statusMap.add(CREATE)
        statusMap.add(WORK)
        statusMap.add(CLEAN)

        sequentialStatusMap = statusMap
    }

    fun startFlow() {
        onStateChanged(metaDataModel!!.state!!)
    }

    private fun onStateChanged(state: String) {
        metaDataModel!!.state = state

        when (state) {
            CREATE -> {
                val createWorkItem = CreateWorkItem(metaDataModel!!)
                createProcess!!.queueWorkItem(createWorkItem)
            }
            WORK -> {
                val workWorkItem = WorkWorkItem(metaDataModel!!)
                workProcess!!.queueWorkItem(workWorkItem)
            }
            CLEAN -> {
                val cleanWorkItem = CleanWorkItem(metaDataModel!!)
                cleanProcess!!.queueWorkItem(cleanWorkItem)
            }
            else -> Log.e(TAG, "error of the flow, something went wrong")
        }

        queueFlowListener.onWorkItemStateChange(metaDataModel!!)
    }

    override fun onJobComplete(workItem: BaseWorkItem) {
        val currentState = workItem.metadata.state

        val nextStateIndex = sequentialStatusMap!!.indexOf(currentState)
        val nextState = sequentialStatusMap!![nextStateIndex + 1]

        onStateChanged(nextState)
    }

    override fun onJobFailed(e: Exception, workItem: BaseWorkItem) {
        workItem.metadata.statusReason = e.message
        workItem.metadata.stateBeforeFail = workItem.metadata.state
    }

    companion object {
        private val TAG : String? = QueueFlowManager::class.java.simpleName
        val SECONDS_IN_ONE_PROCESS : Long? = 5 * 1000

        private var sequentialStatusMap: List<String>? = null
    }
}
