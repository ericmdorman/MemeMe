package com.example.mememe;

import android.net.Uri;
import android.view.View;
import android.widget.EditText;

import com.facebook.drawee.view.SimpleDraweeView;

public class MemeViewController {
    private final SimpleDraweeView mImageView;
    private final EditText mUrlInput;
    private final View mSubmitButton;
    private final View mContainerView;
    private final EditText[] mTitleEditTexts;

    public MemeViewController(View rootView) {
        mContainerView = rootView.findViewById(R.id.meme_container);
        mImageView = rootView.findViewById(R.id.meme_image_view);
        mUrlInput = rootView.findViewById(R.id.meme_url_input);
        mSubmitButton = rootView.findViewById(R.id.meme_url_submit_button);
        mTitleEditTexts = new EditText[]{
                rootView.findViewById(R.id.top_title_text),
                rootView.findViewById(R.id.bottom_title_text)
        };
        init();
    }

    private void init() {
        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // https://mondrian.mashable.com/uploads%252Fcard%252Fimage%252F109036%252Fcfbedff0c19247c6a6106722ea586966.png%252F950x534__filters%253Aquality%252880%2529.png?signature=zIU0XnUr3DAt6RHQ153XlGiHpKQ=&source=https%3A%2F%2Fblueprint-api-production.s3.amazonaws.com

                String urlString = mUrlInput.getText().toString();
                Uri uri = Uri.parse(urlString);
                mImageView.setImageURI(uri);
            }
        });
    }

    public View getContainerView() {
        return mContainerView;
    }

    public void prepareForSnapshot() {
        for (EditText editText : mTitleEditTexts) {
            if (editText.getText().toString().trim().isEmpty()) {
                editText.setVisibility(View.GONE);
            }
            editText.setCursorVisible(false);
        }
    }

    public void cleanupAfterSnapshot() {
        for (EditText editText : mTitleEditTexts) {
            editText.setVisibility(View.VISIBLE);
            editText.setCursorVisible(true);
        }
    }
}
