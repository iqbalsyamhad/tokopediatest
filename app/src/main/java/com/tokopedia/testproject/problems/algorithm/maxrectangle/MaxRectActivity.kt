package com.tokopedia.testproject.problems.algorithm.maxrectangle

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.tokopedia.testproject.R
import com.tokopedia.testproject.loadFile
import kotlinx.android.synthetic.main.activity_problem.*

class MaxRectActivity : AppCompatActivity() {
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_problem)
        webView.loadFile("max_rect.html")
        tvInstruction.setText("Input")

        val matrix = arrayOf(
                intArrayOf(0, 0, 1, 0, 1),
                intArrayOf(0, 0, 1, 1, 1),
                intArrayOf(1, 1, 1, 1, 1),
                intArrayOf(0, 1, 1, 1, 1),
                intArrayOf(1, 0, 0, 0, 1))

        var input = ""
        for (row in matrix) {
            for (col in row) {
                input = "$input$col "
            }
            input += "\n"
        }

        tvInput.setText(input)
        // example of how to call the function
        val result = Solution.maxRect(matrix)

        tvResult.setText("Output $result")
    }

}