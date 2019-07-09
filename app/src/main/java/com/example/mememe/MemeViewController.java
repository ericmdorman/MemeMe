package com.example.mememe;

import android.net.Uri;
import android.view.View;
import android.widget.EditText;

import com.facebook.drawee.view.SimpleDraweeView;

class MemeViewController {
    private final SimpleDraweeView mImageView;
    private final EditText mUrlInput;
    private final View mSubmitButton;

    public MemeViewController(View rootView) {
        mImageView = rootView.findViewById(R.id.meme_image_view);
        mUrlInput = rootView.findViewById(R.id.meme_url_input);
        mSubmitButton = rootView.findViewById(R.id.meme_url_submit_button);
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
}
