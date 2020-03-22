package com.test.fliprhack_trello;

import android.widget.Filter;

import java.util.ArrayList;
import java.util.List;



public class FilterHelper extends Filter {
    static List<CardModelClass> currentList;
    static MyAdapter adapter;
    public static FilterHelper newInstance(List<CardModelClass> currentList, MyAdapter adapter) {
        FilterHelper.adapter=adapter;
        FilterHelper.currentList=currentList;
        return new FilterHelper();
    }
    /*
    - Perform actual filtering.
     */
    @Override
    protected FilterResults performFiltering(CharSequence constraint) {
        FilterResults filterResults=new FilterResults();

        if(constraint != null && constraint.length()>0)
        {
            //CHANGE TO UPPER
            constraint=constraint.toString().toUpperCase();

            //HOLD FILTERS WE FIND
            ArrayList<CardModelClass> foundFilters=new ArrayList<>();

            String name,desc;

            //ITERATE CURRENT LIST
            for (int i=0;i<currentList.size();i++)
            {

                name= currentList.get(i).getName();

                //SEARCH
                if(name.toUpperCase().contains(constraint)){
                    foundFilters.add(currentList.get(i));
                }else if(name.toUpperCase().contains(constraint)){
                    foundFilters.add(currentList.get(i));
                }
            }

            //SET RESULTS TO FILTER LIST
            filterResults.count=foundFilters.size();
            filterResults.values=foundFilters;
        }else
        {
            //NO ITEM FOUND.LIST REMAINS INTACT
            filterResults.count=currentList.size();
            filterResults.values=currentList;
        }

        //RETURN RESULTS
        return filterResults;
    }

    @Override
    protected void publishResults(CharSequence charSequence, FilterResults filterResults) {

        adapter.cardModelClasses= (ArrayList<CardModelClass>) filterResults.values;
        adapter.notifyDataSetChanged();
    }
}
//end
