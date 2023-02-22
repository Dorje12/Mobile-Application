package com.example.addu.db.connectivity;
// Imports needed from the processing the class
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

//ActionSqlConnect class extends the SQLiteOpenHelper in order to access the working procedure with the database.
// a helper class used to manage the database creation and version management.
public class ActionSqlConnect extends SQLiteOpenHelper {
    //Declaring the file name of the file that will be stored in the database system
    public static final String SqlDbLite = "Access.db";
    public ActionSqlConnect(Context context) {
        super(context,"Access.db",null,1);
    }
    //Method which is called when the project is created. That is when the first database entry where the first the table is added in the system.
    @Override
    public void onCreate(SQLiteDatabase openOwnDb) {
// a helper class used to manage the database creation and version management.
//onCreate() is called when the activity is first created where all the normal static is stored in like create.
        openOwnDb.execSQL("create Table users(email TEXT primary key, password TEXT, first_name TEXT," + "last_name TEXT,nick_name TEXT,born_year TEXT,bio TEXT,date_edit TEXT)");openOwnDb.execSQL("create Table posts(id integer primary key AUTOINCREMENT,content TEXT,date_create TEXT," +
                "email TEXT references users(email) )");
    }
    //Method to handle requests from the javaclass when updating any the data in the db.
    @Override
    public void onUpgrade(SQLiteDatabase openOwnDb, int i, int newVersion) {
        //onUpgrade is called only when the database file exists
        //SQLiteDatabase objects helps in running queries that will be used to help in dropping or deleting the data table if any
        //kind of table that trying to create already exists.
        // If the user writes email that already has already been registered in the system
        // then this code will drop the table.
        openOwnDb.execSQL("drop Table if exists users");
        openOwnDb.execSQL("drop Table if exists posts");
    }
    //Method which is used for the adding any kind of data that the user enters into the user table.
    //like firstname, lastname, pet name, born year, bio and many more
    public Boolean saveChangeInData(String email, String password, String first_name,String last_name, String nick_name, String born_year, String bio, String date_edit) {
        SQLiteDatabase openOwnDb = this.getWritableDatabase();
        ContentValues valuesUpdated = new ContentValues();
        valuesUpdated.put("email",email);
        valuesUpdated.put("password",password);
        valuesUpdated.put("first_name",first_name);
        valuesUpdated.put("last_name",last_name);
        valuesUpdated.put("nick_name",nick_name);
        valuesUpdated.put("born_year",born_year);
        valuesUpdated.put("bio",bio);
        valuesUpdated.put("date_edit",date_edit);
        //Execution of query for inserting the values that user passed into the database.
        long outcomeAccess = openOwnDb.insert("users",null,valuesUpdated);
        //Simple logic statement for confirming / checking if data that the user has inserted has passed or not.
        if(outcomeAccess != -1) {
            return true;
        }
        else{
            return false;
        }
    }
    //Method which is used for the adding any kind of data that the user enters into the post table.
    public Boolean saveChangeInDataPosts(String content, String date_create,String email) {
        SQLiteDatabase openOwnDb = this.getWritableDatabase();
        ContentValues valuesUpdated = new ContentValues();
        valuesUpdated.put("content",content);
        valuesUpdated.put("date_create",date_create);
        valuesUpdated.put("email",email);
        //Execution of query for inserting the values that user passed into the database.
        long outcomeAccess = openOwnDb.insert("posts",null,valuesUpdated);
        // if else logic condition for confirming / checking if data that the user has inserted has passed or not.
        if(outcomeAccess != -1) {
            return true;
        }
        else{
            return false;
        }
    }
    //Method created for validation and authentication
    public Boolean validateOnNewSignUpEmail(String email) {
        SQLiteDatabase openOwnDb = this.getWritableDatabase();
        //Execution of queries  that confirm whether the user emailaddress exist or not
        //with if else condition
        //to send out the result to the java class.
        Cursor pointOutReqData = openOwnDb.rawQuery("Select * from users where email = ?", new String[] {email});
        if (pointOutReqData.getCount() < 1) {
            return false;
        }
        else{
            return true;
        }
    }
    //Java method in order to handle the validation and authentication work.
    public Boolean validateOnNewSignUpNickName(String nick_name) {
        SQLiteDatabase openOwnDb = this.getWritableDatabase();
        //Execution of queries to check if
        //the user nickname exists or not,
        //with the if else condition
        //to send the result or findings to the class.
        Cursor pointOutReqData = openOwnDb.rawQuery("Select * from users where nick_name = ?", new String[] {nick_name});
        if (pointOutReqData.getCount() < 1) {
            return false;
        }
        else{
            return true;
        }
    }
    //Main method for validating a user to check whether the user is authenticated or not.
    public Boolean validateOnNewSignUp(String email,String password) {
        SQLiteDatabase openOwnDb = this.getWritableDatabase();
        //Query for getting the user name and password
        //in the database.
        //if else condition to send the result for the verification of the username and password
        Cursor pointOutReqData = openOwnDb.rawQuery("Select * from users where email = ? and password = ?", new String[] {email,password});
        if (pointOutReqData.getCount() < 1) {
            return false;
        }
        else{
            return true;
        }
    }
    //Method for returning values of users whose email is passed on
    //As the argument statement.
    //All the values of users is returned with the use of cursor.
    public Cursor getSpecificData(String emailOnBoard) {
        SQLiteDatabase showOwnData = this.getWritableDatabase();
        String query = "SELECT * FROM users WHERE email ='" +emailOnBoard  + "'";
        Cursor getReqData =  showOwnData.rawQuery(query,null);
        return  getReqData;
    }
    //Method that searches the nickname of the user with the email passed in the argument and returns it.
    public Cursor obtainNickName(String email){
        SQLiteDatabase showOwnData = this.getWritableDatabase();
        String query = "SELECT * FROM users WHERE email ='" + email  + "'";
        Cursor getReqData =  showOwnData.rawQuery(query,null);
        return  getReqData;
    }
    //Whenever any change is made in the database with the help of edit functionality in the users table,
    //This method is executed in order to update the data that the user wants to update.
    public Boolean saveChangeInUserProfile(String email,String first_name,String nick_name, String born_year,String bio,String date_edit) {
        SQLiteDatabase openOwnDb = this.getWritableDatabase();ContentValues valuesUpdated = new ContentValues();
        valuesUpdated.put("nick_name",nick_name);valuesUpdated.put("born_year",born_year);
        valuesUpdated.put("bio",bio);valuesUpdated.put("date_edit",date_edit);
        //Query execution for updating the user details based on the email of the user.
        Cursor makeChange = openOwnDb.rawQuery("Select * from users where email=?", new String[] {email});
        if(makeChange.getCount() >0) {
            long outcomeAccess = openOwnDb.update("users",valuesUpdated,"email=?",new String[] {email});
            if(outcomeAccess != -1) {
                return true;
            }
            else{
                return false;
            }
        }
        else{
            return false;
        }
    }
    //Whenever any change is made in the database with the help of edit functionality in the posts table,
    //This method is executed in order to update the data that the user wants to update.
    public Boolean saveChangeInDataPostsEdit(String id,String content) {
        SQLiteDatabase openOwnDb = this.getWritableDatabase();
        ContentValues valuesUpdated = new ContentValues();
        valuesUpdated.put("content",content);
        //Query execution for updating the user details based on the id of the user.
        Cursor makeChange = openOwnDb.rawQuery("Select * from posts where id=?",new String[] {id});
        if (makeChange.getCount() >0) {
            long outcomeAccess = openOwnDb.update("posts", valuesUpdated, "id=?", new String[]{id});
            if(outcomeAccess != -1) {
                return true;
            }
            else{
                return false;
            }
        }
        else{
            return false;
        }
    }
    //Method that returns the list of the posted comments to the user,
    //So that they would be able to view all the posted comments.
    public Cursor obtainPostListComment() {
        SQLiteDatabase helpObtainData = this.getWritableDatabase();
        Cursor pointOutPostComment = helpObtainData.rawQuery("Select * from posts order by date_create desc",null);
        return  pointOutPostComment;
    }
    //Method that return each user their own posted comments only.
    //This method uses the foreign key email from the users table in order to detect the comments of the user.
    public Cursor obtainPostListCommentUser(String email) {
        SQLiteDatabase helpObtainData = this.getWritableDatabase();
        String rawRun = "SELECT * FROM posts WHERE email ='" + email  + "' order by date_create desc";
        Cursor pointOutPostComment = helpObtainData.rawQuery(rawRun,null);
        return  pointOutPostComment;
    }
    //This method return all the data and the information that the database is holding
    //about the posts id that is passed as the argument.
    public Cursor obtainPostListCommentUserId(String id) {
        SQLiteDatabase helpObtainData = this.getWritableDatabase();
        String rawRun = "SELECT * FROM posts WHERE id ='" + id + "' order by date_create desc";
        Cursor pointOutPostComment = helpObtainData.rawQuery(rawRun,null);
        return  pointOutPostComment;
    }
    //Method in order to delete the posts in accordance with the id.
    public Boolean statusGoneDeletePost(String id) {
        SQLiteDatabase openOwnDb = this.getWritableDatabase();
        //Query execution for deleting the required data with the help of post id along with the simple logic
        //that verifies user whether the post has been deleted or not.
        Cursor makeChange = openOwnDb.rawQuery("Select * from posts where id=?",new String[] {id});
        if (makeChange.getCount() >0) {
            long outcomeAccess = openOwnDb.delete("posts", "id=?", new String[]{id});
            if(outcomeAccess != -1) {
                return true;
            }
            else{
                return false;
            }
        }
        else{
            return false;
        }
    }
    //Method that returns all the user details of the all the users except the user that has been
    //passed as the argument.
    public Cursor getUserExcept(String email) {
        SQLiteDatabase helpObtainData = this.getWritableDatabase();
        String rawRun = "SELECT * FROM users WHERE email !='" + email  + "'";
        Cursor pointOutPostComment = helpObtainData.rawQuery(rawRun,null);
        return  pointOutPostComment;
    }
    //core method in order to delete the posts in accordance with the email.
    //Whenever a user is deleted first all of their posts are deleted here.
    public Boolean statusGoneDeleteUserEmail(String email) {
        SQLiteDatabase openOwnDb = this.getWritableDatabase();
        //Query execution for deleting the required data with the help of post email along with the simple logic
        //that verifies user whether the post has been deleted or not.
        Cursor makeChange = openOwnDb.rawQuery("Select * from posts where email=?",new String[] {email});
        if (makeChange.getCount() >0) {
            long outcomeAccess = openOwnDb.delete("posts", "email=?", new String[]{email});
            if(outcomeAccess != -1) {
                return true;
            }
            else{
                return false;
            }
        }
        else{
            return false;
        }
    }
    //Main method to delete the user in conformity with the email.
    public Boolean statusGoneDeleteUser(String email) {
        SQLiteDatabase openOwnDb = this.getWritableDatabase();
        //Query execution for deleting the required data with the help of user email along with the simple logic
        //that verifies user whether the user has been deleted or not.
        Cursor makeChange = openOwnDb.rawQuery("Select * from users where email=?",new String[] {email});
        if (makeChange.getCount() >0) {
            long outcomeAccess = openOwnDb.delete("users", "email=?", new String[]{email});
            if(outcomeAccess != -1) {
                return true;
            }
            else{
                return false;
            }
        }
        else{
            return false;
        }
    }
}
