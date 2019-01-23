package com.camp.bit.todolist;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.camp.bit.todolist.beans.Note;
import com.camp.bit.todolist.beans.State;
import com.camp.bit.todolist.db.TodoContract;
import com.camp.bit.todolist.db.TodoDbHelper;
import com.camp.bit.todolist.debug.DebugActivity;
import com.camp.bit.todolist.ui.NoteListAdapter;

import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import static java.security.AccessController.getContext;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_ADD = 1002;

    private RecyclerView recyclerView;
    private NoteListAdapter notesAdapter;
    private TodoDbHelper todoDbHelper;
    private SQLiteDatabase database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        todoDbHelper = new TodoDbHelper(this);
        database = todoDbHelper.getReadableDatabase();
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(
                        new Intent(MainActivity.this, NoteActivity.class),
                        REQUEST_CODE_ADD);
            }
        });

        recyclerView = findViewById(R.id.list_todo);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false));
        recyclerView.addItemDecoration(
                new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        notesAdapter = new NoteListAdapter(new NoteOperator() {
            @Override
            public void deleteNote(Note note) {
                MainActivity.this.deleteNote(note);
            }

            @Override
            public void updateNote(Note note) {
                MainActivity.this.updateNode(note);
            }
        });
        recyclerView.setAdapter(notesAdapter);

        notesAdapter.refresh(loadNotesFromDatabase());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        database.close();
        database = null;
        todoDbHelper.close();
        todoDbHelper = null;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_settings:
                return true;
            case R.id.action_debug:
                startActivity(new Intent(this, DebugActivity.class));
                return true;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_ADD
                && resultCode == Activity.RESULT_OK) {
            notesAdapter.refresh(loadNotesFromDatabase());
        }
    }

    private List<Note> loadNotesFromDatabase() {
        // TODO 从数据库中查询数据，并转换成 JavaBeans

        if(database == null){
            return Collections.emptyList();
        }
        List<Note> notes = new LinkedList<>();
        Cursor cursor = null;
        try{
            cursor = database.query(TodoContract.TodoEntry.TABLE_NAME,
                    new String[]{TodoContract.TodoEntry.COLUMN_NAME_CONTENT,
                            TodoContract.TodoEntry.COLUMN_NAME_STATE,TodoContract.TodoEntry.COLUMN_NAME_DATE},
                    null,null,null,null,TodoContract.TodoEntry.COLUMN_NAME_DATE + " DESC");
            while (cursor.moveToNext()){

//                int id =
//                int id = cursor.getInt(cursor.getColumnIndex(TodoContract.TodoEntry._ID));
                String content = cursor.getString(cursor.getColumnIndex(TodoContract.TodoEntry.COLUMN_NAME_CONTENT));
                long dateMs = cursor.getLong(cursor.getColumnIndex(TodoContract.TodoEntry.COLUMN_NAME_DATE));
                int intState = cursor.getInt(cursor.getColumnIndex(TodoContract.TodoEntry.COLUMN_NAME_STATE));


                Note note = new Note();
//                note.setId(id);
                note.setContent(content);
                note.setDate(new Date(dateMs));
                note.setState(State.from(intState));

                notes.add(note);
            }
        }finally {
            if(cursor != null){
                cursor.close();
            }
        }
        return notes;
    }

    private void deleteNote(Note note) {
        // TODO 删loadNotesFromDatabase除数据
        database.execSQL("DELETE FROM " + TodoContract.TodoEntry.TABLE_NAME +
        " WHERE " + TodoContract.TodoEntry.COLUMN_NAME_DATE + "="  +note.getDate().getTime());
        notesAdapter.refresh(loadNotesFromDatabase());
    }

    private void updateNode(Note note) {
        // 更新数据
    }

}
