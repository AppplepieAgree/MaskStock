package com.appplepie.maskstock;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.Holder> {

    private String urlList[] = {"https://www.mfds.go.kr/brd/m_99/view.do?seq=43991",
            "http://blog.naver.com/kfdazzang/221837044802",
            "http://blog.naver.com/kfdazzang/221844817502",
            "https://www.mfds.go.kr/brd/m_659/list.do"
    };


    @NonNull
    @Override
    public RecyclerViewAdapter.Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_more_info, parent, false);

        return new RecyclerViewAdapter.Holder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.Holder holder, int position) {
        holder.myWebView.load(urlList[position]);

    }

    @Override
    public int getItemCount() {

        return urlList.length;
    }

    public class Holder extends RecyclerView.ViewHolder {

        MyWebView myWebView;
        public Holder(@NonNull View itemView) {
            super(itemView);

            myWebView = itemView.findViewById(R.id.webview);
        }
    }
}
