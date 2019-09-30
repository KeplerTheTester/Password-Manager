package com.example.passwordholder;

import android.app.Application;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import java.util.ArrayList;
import java.util.List;

public class RecyclerAdapterHeader extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;
    List<Object> objects = new ArrayList<>();

    public RecyclerAdapterHeader(List<Object> objects)
    {
        this.objects = objects;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        if(viewType == TYPE_HEADER)
        {
            view = layoutInflater.inflate(R.layout.recyclerview_header, null);
            System.out.println("This is a header type view");
            return new VHHeader(view);
        }
        else if(viewType == TYPE_ITEM)
        {
            view = layoutInflater.inflate(R.layout.recyclerview_category, null);
            System.out.println("This is an item type view");
            return new VHItem(view) ;
        }
        else
        {
            throw new RuntimeException("not a valid viewType");
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof VHHeader)
        {

            //((VHHeader) holder).textView.setText("test");
            Headers headers = (Headers) objects.get(position);
            ((VHHeader) holder).textView.setText(headers.getTitle());
        }
        else if(holder instanceof VHItem)
        {
            Category category = (Category) objects.get(position);
            ((VHItem) holder).title.setText(category.getItemDetail());
        }
    }

    @Override
    public int getItemCount() {
        return objects.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (isPositionHeader(position))
            return TYPE_HEADER;

        return TYPE_ITEM;
    }

    private boolean isPositionHeader(int position) {
        if(objects.get(position) instanceof Headers)
            return true;
        else if(objects.get(position) instanceof Category)
            return false;
        else
            throw new RuntimeException("Not valid class -- PositionHeader");
    }

    private Object getItem(int position) {
        //What type of things it must return
        //return objects.get(position-1);
        if(objects.get(position-1) instanceof Category)
        {
            return (Category) objects.get(position-1);
        }
        else if(objects.get(position-1) instanceof Headers)
        {
            return (Headers) objects.get(position-1);
        }
        else
        {
            return new RuntimeException("Not a valid class");
        }
    }

    void swap(ArrayList<Object> arrayList)
    {

        //method that allows the data in the recycler to update, and allows recyclerview to know and
        //update its UI
        objects.clear();
        objects.addAll(arrayList);
        notifyDataSetChanged();
    }

    void removeItem(int position)
    {
        objects.remove(position);
        notifyItemChanged(position);
        notifyItemRangeRemoved(position, 1);
        //then have this interact with the database. Check if it works first
    }

    class VHItem extends RecyclerView.ViewHolder {
        TextView title;

        public VHItem(final View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.category_title);
            title.setText("un object");
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    Toast.makeText(view.getContext(), "On long clicked", Toast.LENGTH_SHORT).show();
                    AppDatabase db = Room.databaseBuilder(view.getContext(),
                            AppDatabase.class, "database-name").allowMainThreadQueries().build();
                    Category categoryToDelete = (Category) objects.get(getAdapterPosition());
                    int currentPosition = getAdapterPosition();
                    //Checks if the previous element in the list is an object with headers,  so
                    //this might be the first item alphabetically in  the header
                    boolean headerLast = (objects.get(currentPosition-1) instanceof Headers);
                    boolean lastElement = ((currentPosition+1) >= objects.size()) ? true: false;
                    boolean headerNext = false;
                    if(!lastElement)
                    {
                        headerNext = (objects.get(currentPosition+1) instanceof Headers);
                    }

                    //this is to check the size of the array adapter and if index is greater than size-1
                    //return falls
                    //might have to make a current position number
                    //removeItem(getAdapterPosition());
                    if(headerNext && headerLast)
                    {
                        removeItem(currentPosition);
                        removeItem(currentPosition-1);
                    }
                    else if(headerLast &&(currentPosition == objects.size()-1))
                    {
                        removeItem(currentPosition);
                        removeItem(currentPosition-1);
                    }
                    else
                    {
                        removeItem(currentPosition);
                    }

                    /*if(objects.get(getAdapterPosition()-1) instanceof Headers )
                    {
                        if(objects.size() > getAdapterPosition()+1  && objects.get(getAdapterPosition()+1) instanceof Headers)
                        {
                            //here it checks if the next item is a header.  then it will know it is the last item

                            removeItem(getAdapterPosition()-1);
                            //then remove  that header
                        }
                        //changed  order
                    }*/
                    //issue here in this code

                    //items are not getting removed here because UID is not contained {bad code issues}=
                    //cant remove before, else it creates a -1 index, crashes
                    //bottom code to remove from database

                    //under the assumption that the list is properly ordered.
                    System.out.println(headerLast);
                    System.out.println(headerNext);
                    db.categoryDao().delete(categoryToDelete);
                    List<Category> test = db.categoryDao().getAllSorted();
                    test.size();
                    return false;

                    //Think about it in the sense of explaining something simple to code
                }
            });
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(view.getContext(), "Simple click", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    class VHHeader extends RecyclerView.ViewHolder {
        TextView textView;

        public VHHeader(View itemView) {

            super(itemView);
            textView = itemView.findViewById(R.id.header_title);
            textView.setText("Titre");

        }
    }


}
