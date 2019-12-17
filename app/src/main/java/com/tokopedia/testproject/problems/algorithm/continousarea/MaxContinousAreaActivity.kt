package com.tokopedia.testproject.problems.algorithm.continousarea

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.tokopedia.testproject.R
import com.tokopedia.testproject.loadFile
import kotlinx.android.synthetic.main.activity_problem.*

class MaxContinousAreaActivity : AppCompatActivity() {
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_problem)
        webView.loadFile("max_continuous_area.html")
        tvInstruction.setText("Input")

        val matrix = arrayOf(
                intArrayOf(1, 0, 0, 1, 0),
                intArrayOf(0, 0, 1, 1, 1),
                intArrayOf(1, 0, 0, 0, 1),
                intArrayOf(0, 0, 1, 0, 1),
                intArrayOf(1, 0, 1, 1, 1))

        var input = ""
        for (row in matrix) {
            for (col in row) {
                input = "$input$col "
            }
            input += "\n"
        }

        tvInput.setText(input)

        val result = Solution.maxContinuousArea(matrix)

        tvResult.setText("Output $result")
    }
}


