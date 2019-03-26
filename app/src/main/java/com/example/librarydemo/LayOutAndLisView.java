package com.example.librarydemo;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.librarydemo.DBBook.Book;
import com.example.librarydemo.DBBook.BookAdapter;
import com.example.librarydemo.DBUser.User;
import com.example.librarydemo.Database.SQLSever;

import java.util.ArrayList;

public class LayOutAndLisView extends AppCompatActivity


        implements NavigationView.OnNavigationItemSelectedListener {
    public static final String EXTRA_IMGBOOK = "com.example.application.example.EXTRA_IMGBOOK";
    public static final String EXTRA_BOOKID = "com.example.application.example.EXTRA_BOOKID";
    public static final String EXTRA_TENSACH = "com.example.application.example.EXTRA_TENSACH";
    public static final String EXTRA_THELOAI = "com.example.application.example.EXTRA_THELOAI";
    public static final String EXTRA_TACGIA = "com.example.application.example.EXTRA_TACGIA";
    public static final String EXTRA_NAMXB = "com.example.application.example.EXTRA_NAMXB";
    public static final String EXTRA_SOLUONG = "com.example.application.example.EXTRA_SOLUONG";


    private ListView lv;
    public static ArrayList<Book> Book_Deefault;

    public static ArrayList<Book> getBook() {
        return Book_Deefault;
    }

    private void setBook(ArrayList<Book> book) {
        this.Book_Deefault = book;
    }

    private BookAdapter adapter;
    public static User user_pro;

    public static User getUser() {
        return user_pro;
    }

    public void setUser(User user) {
        this.user_pro = user;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lay_out_and_lis_view);

        AnhXa();
        ArrayBook();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);



    }

    public void ArrayBook(){

        ArrayList<Book> book = new ArrayList<>();
        book.add(new Book(1, "Để Con Được Ốm", "Sách Tự Lực", "Uyên Bùi - BS. Trí Đoàn","2016",R.drawable.book_1, 100));
        book.add(new Book(2, "Đọc Vị Bất Kỳ Ai", "Sách Tự Lực", "TS. David J. Lieberman","2015",R.drawable.book_2, 100));
        book.add(new Book(3, "Nghệ Thuật Bán Hàng Bậc Cao", "Nghề Bán Hàng", "Zig Zig Lar","2008",R.drawable.book_3, 100));
        book.add(new Book(4, "Dấn Thân", "Tiểu Sử", "Sheryl Sandberg","2014",R.drawable.book_4, 100));
        book.add(new Book(5, "Sức Mạnh Của Ngôn Từ", "Văn học", "Vô Danh","TB-2018",R.drawable.book_5, 100));
        book.add(new Book(6, "Đắc Nhân Tâm", "Phi Hư Cấu", "Dale Carnegie","2013",R.drawable.book_6, 100));
        book.add(new Book(7, "Nhà Giả Kim", "Tiểu Thuyết", "Paulo Coelho","2013",R.drawable.book_7, 100));
        this.setBook(book);
        adapter = new BookAdapter(this, R.layout.elemen_book, book);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                OpenThongTinSach(position);
            }
        });
    }

    public void OpenThongTinSach(int position){
        Intent intent = new Intent(this, BookInformation.class);
        intent.putExtra(EXTRA_IMGBOOK, Book_Deefault.get(position).getImgBook());
        intent.putExtra(EXTRA_BOOKID, Book_Deefault.get(position).getBookID());
        intent.putExtra(EXTRA_TENSACH, Book_Deefault.get(position).getTenSach());
        intent.putExtra(EXTRA_THELOAI, Book_Deefault.get(position).getTheLoai());
        intent.putExtra(EXTRA_TACGIA, Book_Deefault.get(position).getTacGia());
        intent.putExtra(EXTRA_NAMXB, Book_Deefault.get(position).getNamXB());
        intent.putExtra(EXTRA_SOLUONG, Book_Deefault.get(position).getSoLuong());
        startActivity(intent);
    }
    public void AnhXa(){
        lv= (ListView) findViewById(R.id.arraybook);
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.lay_out_and_lis_view, menu);
        TextView ten = (TextView) findViewById(R.id.Text_Name);
        TextView email = (TextView) findViewById(R.id.Text_Gmail);
        TextView trangthai = (TextView) findViewById(R.id.Text_TrangThai);

        Intent intent = getIntent();
        final String tt_acc = intent.getStringExtra(Login.EXTRA_USER);
        final SQLSever sqlSever = new SQLSever(this);
        User s = sqlSever.getUser(tt_acc);

        ten.setText(s.getFullname());
        email.setText(s.getGmail());
        trangthai.setText(s.getStatus());
        this.setUser(s);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_Search) {
            return true;
        }
        if (id == R.id.action_Log) {
            Intent intent = new Intent(this, ArrayLog.class);
            startActivity(intent);
            return true;
        }
        if (id == R.id.action_UpdateBook) {
            Intent intent = new Intent(this, UpdateBook.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            Intent intent = new Intent(this, UserInformation.class);
            startActivity(intent);
        } else if (id == R.id.nav_gallery) {
            Intent intent = new Intent(this, ChangPass.class);
            startActivity(intent);
        } else if (id == R.id.nav_slideshow) {
            AlertDialog.Builder b=new AlertDialog.Builder(LayOutAndLisView.this);
            b.setTitle("Đăng Xuất");
            b.setMessage("Bạn có muốn đăng xuất?");
            b.setIcon(R.drawable.icons_out);
            b.setPositiveButton("Yes", new DialogInterface. OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which)
                {
                    OpenLogin();
                    finish();
                }});
            b.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which)
                {
                    dialog.cancel();
                }
            });
            b.create().show();
        } else {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    public void OpenLogin(){
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
    }
}
