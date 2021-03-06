package com.example.events.ui.main;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.events.CalendarFragment;
import com.example.events.R;
import com.example.events.models.Event;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.MyItem> {
    private Context context;
    private ArrayList<Event> event;
    private CalendarFragment fragmentToWorkWithArr;

    public EventAdapter(ArrayList<Event> e, Context c, CalendarFragment fragment) {
        this.context = c;
        this.event = e;
        fragmentToWorkWithArr = fragment;
    }

    @NonNull
    @NotNull
    @Override
    public EventAdapter.MyItem onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_layout, parent,false);
        return new MyItem(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull EventAdapter.MyItem holder, int position) {
        holder.nameView.setText(event.get(position).getTitle());
        holder.descriptionView.setText(event.get(position).getDescription());
        holder.timeView.setText(event.get(position).getTime());
        holder.tagsView.setText(event.get(position).getTags());
        holder.deleteImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentToWorkWithArr.deleteEvent(position);

            }
        });
    }

    @Override
    public int getItemCount() {
        return event.size();
    }
    public class MyItem extends RecyclerView.ViewHolder{
        private TextView nameView;
        private TextView descriptionView;
        private TextView timeView;
        private TextView tagsView;
        private ImageView deleteImage;

        public MyItem(@NonNull @NotNull View itemView) {
            super(itemView);
            nameView = itemView.findViewById(R.id.nameEvent);
            descriptionView = itemView.findViewById(R.id.Description);
            timeView = itemView.findViewById(R.id.timeStart);
            tagsView = itemView.findViewById(R.id.tags);
            deleteImage = itemView.findViewById(R.id.deleteImage);
        }
    }
}
