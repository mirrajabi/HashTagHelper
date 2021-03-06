package com.volokh.danylo.example;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.volokh.danylo.hashtaghelper.HashTagHelper;
import com.volokh.danylo.hashtaghelper.OnHashTagClickListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HashTagHelperDemoActivity extends AppCompatActivity implements OnHashTagClickListener, View.OnClickListener {

    private static final String TAG = HashTagHelperDemoActivity.class.getSimpleName();

    private HashTagHelper mTextHashTagHelper;
    private HashTagHelper mEditTextHashTagHelper;

    private TextView mEditTextView;
    private TextView mHashTagText;
    private TextView mAllHashTag;

    private Toast mToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hash_tag_helper_demo);

        mHashTagText = (TextView) findViewById(R.id.text);
        mAllHashTag = (TextView) findViewById(R.id.all_hashtags);
        mEditTextView = (EditText) findViewById(R.id.edit_text_field);

        Button getEnteredText = (Button) findViewById(R.id.get_entered_text_btn);
        getEnteredText.setOnClickListener(this);
        Button getAllHashTagsBtn = (Button) findViewById(R.id.get_all_hashtags_btn);
        getAllHashTagsBtn.setOnClickListener(this);

        char[] additionalSymbols = new char[]{
                '_',
                '$'
        };
        // If you set additional symbols not only letters and digits will be a valid symbols for hashtag
        // Example: "hash_tag_with_underscore_and$dolar$sign$is$also$valid_hashtag"
        mTextHashTagHelper = HashTagHelper.Creator.create(getResources().getColor(R.color.colorPrimaryDark), this, Arrays.asList('#','@','-'), additionalSymbols);
        mTextHashTagHelper.handle(mHashTagText);

        // Here we don't specify additionalSymbols. It means that in EditText only letters and digits will be valid symbols
        List<Character> startingChars = new ArrayList<>();
        startingChars.add('@');
        startingChars.add('#');
        startingChars.add('-');
        mEditTextHashTagHelper = HashTagHelper.Creator.create(getResources().getColor(R.color.colorPrimaryDark), null, Arrays.asList('#','@','-'));
        mEditTextHashTagHelper.handle(mEditTextView);
    }

    @Override
    public void onHashTagClicked(String hashTag) {
        Log.v(TAG, "onHashTagClicked [" + hashTag + "]");
        if(mToast != null){
            mToast.cancel();
        }
        mToast = Toast.makeText(HashTagHelperDemoActivity.this, hashTag, Toast.LENGTH_SHORT);
        mToast.show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.get_entered_text_btn:
                mHashTagText.setText(mEditTextView.getText());
                break;
            case R.id.get_all_hashtags_btn:
                List<String> allHashTags = mTextHashTagHelper.getAllHashTags();
                mAllHashTag.setText(allHashTags.toString());
                break;
        }
    }
}
