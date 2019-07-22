package dajver.com.taskqueueexample

import android.os.Bundle
import android.os.CountDownTimer
import androidx.appcompat.app.AppCompatActivity
import dajver.com.taskqueueexample.models.MetaDataModel
import dajver.com.taskqueueexample.queue.enums.Statuses
import dajver.com.taskqueueexample.queue.flow.QueueFlowListener
import dajver.com.taskqueueexample.queue.flow.QueueFlowManager
import kotlinx.android.synthetic.main.activity_main.*
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity(), QueueFlowListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        startButton.setOnClickListener {
            QueueFlowManager(this).startFlow()
        }
    }

    private val countDownTimer = object : CountDownTimer(6000, 1000) {
        override fun onTick(millisUntilFinished: Long) {
            val convertedTime = TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished).toString()
            timer.text =  "Time left to next state: $convertedTime"
        }

        override fun onFinish() { }
    }

    override fun onWorkItemStateChange(metaDataModel: MetaDataModel) {
        text.text = "Current state of queue: ${metaDataModel.state}"

        if(metaDataModel.state != Statuses.CLEAN) {
            countDownTimer.start()
        }
    }
}
