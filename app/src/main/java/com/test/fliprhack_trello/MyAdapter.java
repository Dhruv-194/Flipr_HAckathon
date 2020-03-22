package com.test.fliprhack_trello;

import android.content.Context;
import android.graphics.Color;
import android.text.Spannable;
import android.text.style.ForegroundColorSpan;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.github.ivbaranov.mli.MaterialLetterIcon;

import java.util.List;
import java.util.Locale;
import java.util.Random;



public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder>
        implements Filterable {

    private final Context c;
    private final int mBackground;
   // private final int[] mMaterialColors;
    public List<CardModelClass> cardModelClasses;
    private List<CardModelClass> filterList;
    private FilterHelper filterHelper;

    interface ItemClickListener {
        void onItemClick(int pos);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements
            View.OnClickListener {
        private final TextView nameTxt;
        private final TextView mDescriptionTxt;

       // private final MaterialLetterIcon mIcon;
        private ItemClickListener itemClickListener;

        ViewHolder(View itemView) {
            super(itemView);
            //mIcon = itemView.findViewById(R.id.mMaterialLetterIcon);
            nameTxt = itemView.findViewById(R.id.mNameTxt);
            mDescriptionTxt = itemView.findViewById(R.id.mDescriptionTxt);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            this.itemClickListener.onItemClick(this.getLayoutPosition());
        }

        void setItemClickListener(ItemClickListener itemClickListener) {
            this.itemClickListener = itemClickListener;
        }
    }
    public MyAdapter(Context mContext, List<CardModelClass> cardModelClasses) {
        this.c = mContext;
        this.cardModelClasses = cardModelClasses;
        this.filterList = cardModelClasses;
        TypedValue mTypedValue = new TypedValue();
        c.getTheme().resolveAttribute(R.attr.selectableItemBackground,
                mTypedValue, true);
        //mMaterialColors = c.getResources().getIntArray(R.array.colors);
        mBackground = mTypedValue.resourceId;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(c).inflate(R.layout.model, parent, false);
        view.setBackgroundResource(mBackground);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //get current scientist
        final CardModelClass s = cardModelClasses.get(position);

        //bind data to widgets
        holder.nameTxt.setText(s.getName());
        holder.mDescriptionTxt.setText(s.getDesc());

        //holder.mIcon.setInitials(true);
       // holder.mIcon.setInitialsNumber(2);
        //holder.mIcon.setLetterSize(25);
       //holder.mIcon.setShapeColor(mMaterialColors[new Random()
       // .nextInt(mMaterialColors.length);
        //holder.mIcon.setLetter(s.getName());

        if(position % 2 == 0){
            holder.itemView.setBackgroundColor(Color.parseColor("#efefef"));
        }

        //get name and galaxy
        String name = s.getName().toLowerCase(Locale.getDefault());


        //highlight name text while searching
        if (name.contains(Utlis.searchString) && !(Utlis.searchString.isEmpty())) {
            int startPos = name.indexOf(Utlis.searchString);
            int endPos = startPos + Utlis.searchString.length();

            Spannable spanString = Spannable.Factory.getInstance().
                    newSpannable(holder.nameTxt.getText());
            spanString.setSpan(new ForegroundColorSpan(Color.RED), startPos, endPos,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

            holder.nameTxt.setText(spanString);
        } else {
            //Utils.show(ctx, "Search string empty");
        }


        //open detailactivity when clicked
        holder.setItemClickListener(pos -> Utlis.sendCardModelClassToActivity(c, s,
                DetailActivity.class));
    }

    @Override
    public int getItemCount() {
        return cardModelClasses.size();
    }

    @Override
    public Filter getFilter() {
        if(filterHelper==null){
            filterHelper= FilterHelper.newInstance(filterList,this);
        }
        return filterHelper;
    }
}
//end