package com.example.moodyeats

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat


class QuizQuestionsActivity : AppCompatActivity(), View.OnClickListener {
lateinit var tv_option_one:TextView
lateinit var tv_option_two:TextView
lateinit var tv_option_three:TextView
lateinit var tv_option_four:TextView
lateinit var btn_submit: Button
lateinit var progressBar:ProgressBar
lateinit var tv_question:TextView
lateinit var tv_progress:TextView
var mCorrectAnswers: Int=0
var check:Int=0
    private var mCurrentPosition: Int = 1 // Default and the first question position
    private var mQuestionsList: ArrayList<Question>? = null

    private var mSelectedOptionPosition: Int = 0


    // TODO (STEP 3: Create a variable for getting the name from intent.)
    // START
    private var mUserName: String? = null
    // END

    /**
     * This function is auto created by Android when the Activity Class is created.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        //This call the parent constructor
        super.onCreate(savedInstanceState)
        mCorrectAnswers=0
        // This is used to align the xml view to this class
        setContentView(R.layout.activity_quiz_questions)
        tv_option_one=findViewById(R.id.tv_option_one)
        tv_option_two=findViewById(R.id.tv_option_two)
        tv_option_three=findViewById(R.id.tv_option_three)
        tv_option_four=findViewById(R.id.tv_option_four)
        btn_submit=findViewById(R.id.btn_submit)
        progressBar=findViewById(R.id.progressBar)
        tv_question=findViewById(R.id.tv_question)
        tv_progress=findViewById(R.id.tv_progress)
        // TODO (STEP 4: Get the NAME from intent and assign it the variable.)
        // START
        mUserName = intent.getStringExtra(Constants.USER_NAME)
        // END
        btn_submit.isEnabled=false
        mQuestionsList = Constants.getQuestions()

        setQuestion()

        tv_option_one.setOnClickListener(this)
        tv_option_two.setOnClickListener(this)
        tv_option_three.setOnClickListener(this)
        tv_option_four.setOnClickListener(this)
        btn_submit.setOnClickListener(this)
    }

    override fun onClick(v: View?) {

        when (v?.id) {

            R.id.tv_option_one -> {
                btn_submit.isEnabled=true
                selectedOptionView(tv_option_one, 1)
            }

            R.id.tv_option_two -> {
                btn_submit.isEnabled=true
                selectedOptionView(tv_option_two, 2)
            }

            R.id.tv_option_three -> {
                btn_submit.isEnabled=true
                selectedOptionView(tv_option_three, 3)
            }

            R.id.tv_option_four -> {
                btn_submit.isEnabled=true
                selectedOptionView(tv_option_four, 4)
            }

            R.id.btn_submit -> {
                v
                tv_progress.text=mCorrectAnswers.toString()
                if (mSelectedOptionPosition == 0) {

                    mCurrentPosition++

                    when {

                        mCurrentPosition <= mQuestionsList!!.size -> {

                              // Assigning scores with respect to options
                                if(mSelectedOptionPosition == 1)
                                    mCorrectAnswers = mCorrectAnswers + 4
                                else if(mSelectedOptionPosition == 2)
                                    mCorrectAnswers = mCorrectAnswers + 3
                                else if(mSelectedOptionPosition == 3)
                                    mCorrectAnswers = mCorrectAnswers + 2
                                else
                                    mCorrectAnswers = mCorrectAnswers + 1


                            setQuestion()
                        }
                        else -> {

                            // TODO (STEP 5: Now remove the toast message and launch the result screen which we have created and also pass the user name and score details to it.)
                            // START
                            val intent =
                                    Intent(this@QuizQuestionsActivity, ResultActivity::class.java)
                            //intent.putExtra(Constants.USER_NAME, mUserName)
                            intent.putExtra(Constants.CORRECT_ANSWERS_SCORE, mCorrectAnswers)
                            startActivity(intent)
                            finish()
                            // END
                        }
                    }
                } else {


                    // This is to check if the answer is wrong
                      // Assigning scores with respect to options
                        if(mSelectedOptionPosition == 1)
                            mCorrectAnswers = mCorrectAnswers + 4
                        else if(mSelectedOptionPosition == 2)
                            mCorrectAnswers = mCorrectAnswers + 3
                        else if(mSelectedOptionPosition == 3)
                            mCorrectAnswers = mCorrectAnswers + 2
                        else
                            mCorrectAnswers = mCorrectAnswers + 1


                    // This is for correct answer
                   // answerView(question.correctAnswer, R.drawable.correct_option_border_bg)

                    if (mCurrentPosition == mQuestionsList!!.size) {
                        btn_submit.text = "FINISH"
                    }
                    else
                    {
                        btn_submit.text = "Next Question"
                    }

                    mSelectedOptionPosition = 0
                    btn_submit.callOnClick()
                }
            }
        }
    }

    /**
     * A function for setting the question to UI components.
     */
    private fun setQuestion() {

        val question = mQuestionsList!!.get(mCurrentPosition - 1) // Getting the question from the list with the help of current position.

        defaultOptionsView()

        if (mCurrentPosition == mQuestionsList!!.size) {
            btn_submit.text = "FINISH"
        } else {
            btn_submit.text = "SUBMIT"
        }

        progressBar.progress = mCurrentPosition
        tv_progress.text = "$mCurrentPosition" + "/" + progressBar.getMax()

        tv_question.text = question.question
        tv_option_one.text = question.optionOne
        tv_option_two.text = question.optionTwo
        tv_option_three.text = question.optionThree
        tv_option_four.text = question.optionFour
        btn_submit.isEnabled=false
    }

    /**
     * A function to set the view of selected option view.
     */
    private fun selectedOptionView(tv: TextView, selectedOptionNum: Int) {

        defaultOptionsView()

        mSelectedOptionPosition = selectedOptionNum

        tv.setTextColor(
            Color.parseColor("#363A43")
        )
        tv.setTypeface(tv.typeface, Typeface.BOLD)
        tv.background = ContextCompat.getDrawable(
                this@QuizQuestionsActivity,
                R.drawable.selected_option_border_bg
        )
    }

    /**
     * A function to set default options view when the new question is loaded or when the answer is reselected.
     */
    private fun defaultOptionsView() {

        val options = ArrayList<TextView>()
        options.add(0, tv_option_one)
        options.add(1, tv_option_two)
        options.add(2, tv_option_three)
        options.add(3, tv_option_four)

        for (option in options) {
            option.setTextColor(Color.parseColor("#7A8089"))
            option.typeface = Typeface.DEFAULT
            option.background = ContextCompat.getDrawable(
                    this@QuizQuestionsActivity,
                    R.drawable.default_option_border_bg
            )
        }
    }

    /**
     * A function for answer view which is used to highlight the answer is wrong or right.
     */
    private fun answerView(answer: Int, drawableView: Int) {

        when (answer) {

            1 -> {
                tv_option_one.background = ContextCompat.getDrawable(
                        this@QuizQuestionsActivity,
                        drawableView
                )
            }
            2 -> {
                tv_option_two.background = ContextCompat.getDrawable(
                        this@QuizQuestionsActivity,
                        drawableView
                )
            }
            3 -> {
                tv_option_three.background = ContextCompat.getDrawable(
                        this@QuizQuestionsActivity,
                        drawableView
                )
            }
            4 -> {
                tv_option_four.background = ContextCompat.getDrawable(
                        this@QuizQuestionsActivity,
                        drawableView
                )
            }
        }
    }
}