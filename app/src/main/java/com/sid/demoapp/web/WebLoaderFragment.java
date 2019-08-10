package com.sid.demoapp.web;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.sid.demoapp.R;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link WebLoaderFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WebLoaderFragment extends Fragment implements View.OnClickListener{
    public static final String TAG = "WebLoaderFragment";

    ResultReceiver resultReceiver;
    private TextView tvWebData;
    private Button btnLoadDataFromWeb;

    public WebLoaderFragment() {
        // Required empty public constructor
    }

    public static WebLoaderFragment newInstance() {
        WebLoaderFragment fragment = new WebLoaderFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        resultReceiver = new WebResultsReceiver(new Handler());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View layout = inflater.inflate(R.layout.fragment_web_loader, container, false);
        tvWebData = (TextView) layout.findViewById(R.id.web_data);
        btnLoadDataFromWeb = (Button) layout.findViewById(R.id.action_load_data_from_web);
        btnLoadDataFromWeb.setOnClickListener(this);

        return layout;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.action_load_data_from_web:
                loadDataFromWeb();
                break;
        }
    }

    private void loadDataFromWeb() {
        final Intent i = new Intent(getActivity(), FetchDataFromWebService.class);
        i.putExtra(FetchDataFromWebService.KEY_RESULT_RECEIVER, resultReceiver);
        getActivity().startService(i);
    }

    public class WebResultsReceiver extends ResultReceiver {
        public static final int RESULT_CODE_OK = 553;
        public static final int RESULT_CODE_ERROR = 807;

        public WebResultsReceiver(Handler handler) {
            super(handler);
        }

        @Override
        protected void onReceiveResult(int resultCode, Bundle resultData) {
            super.onReceiveResult(resultCode, resultData);
            String resultDataString = "";
            switch (resultCode) {
                case RESULT_CODE_OK:
                    resultDataString = resultData.getString(FetchDataFromWebService.KEY_WEB_RESULT);
                    break;
                case RESULT_CODE_ERROR:
                    resultDataString = "No data from Web.";
                    break;
            }
            tvWebData.setText(resultDataString);
        }
    }
}
