package com.troystudios.wackgame;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;

import android.app.LoaderManager.LoaderCallbacks;
import android.content.ContentResolver;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build.VERSION;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.SignInButton;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

import area730.com.first.R;


/**
 * A login screen that offers login via email/password and via Google+ sign in.
 * <p/>
 * ************ IMPORTANT SETUP NOTES: ************
 * In order for Google+ sign in to work with your app, you must first go to:
 * https://developers.google.com/+/mobile/android/getting-started#step_1_enable_the_google_api
 * and follow the steps in "Step 1" to create an OAuth 2.0 client for your package.
 */
public class LoginActivity extends PlusBaseActivity implements LoaderCallbacks<Cursor> {


    private final String TAG = "LoginActivity";

    private UserLoginTask mAuthTask = null;

    // UI references.
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mEmailLoginFormView;
    private SignInButton mPlusSignInButton;
    private View mSignOutButtons;
    private View mLoginFormView;

   // private DBHelper users;
 //   private SQLiteDatabase db;
   // private  Cursor cr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Find the Google+ sign in button.
        mPlusSignInButton = (SignInButton) findViewById(R.id.plus_sign_in_button);
        if (supportsGooglePlayServices()) {
            // Set a listener to connect the user when the G+ button is clicked.
            mPlusSignInButton.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    signIn();
                }
            });

        } else {
            // Don't offer G+ sign in if the app's version is too low to support Google Play
            // Services.
            mPlusSignInButton.setVisibility(View.GONE);
            return;
        }

        // Set up the login form.
        mEmailView = (AutoCompleteTextView) findViewById(R.id.email);
        populateAutoComplete();

        mPasswordView = (EditText) findViewById(R.id.password);

        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);
        mEmailLoginFormView = findViewById(R.id.email_login_form);
        mSignOutButtons = findViewById(R.id.plus_sign_out_buttons);

        //users = new DBHelper(this);//
    }

    private void populateAutoComplete() {
        if (VERSION.SDK_INT >= 14) {
            // Use ContactsContract.Profile (API 14+)
            getLoaderManager().initLoader(0, null, this);
        } else if (VERSION.SDK_INT >= 8) {
            // Use AccountManager (API 8+)
            new SetupEmailAutoCompleteTask().execute(null, null);
        }
    }


    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    public void attemptLogin() {
        if (mAuthTask != null) {
            return;
        }

        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;


        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        } else if (!isEmailValid(email)) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            showProgress(true);
            mAuthTask = new UserLoginTask(email, password);

            mAuthTask.execute((Void) null);
        }
    }

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() >1;
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    public void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    @Override
    protected void onPlusClientSignIn(String email,String code) {
       // String email,String code
        //Set up sign out and disconnect buttons.
        final String em= email;
        final String co= code;
        Button signOutButton = (Button) findViewById(R.id.plus_sign_out_button);
        signOutButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
               // showProgress(true);
                mAuthTask = new UserLoginTask(em, co);
                mAuthTask.execute((Void) null);
               // mAuthTask.execute((Void) null);

                //signOut();
            }
        });
        Button disconnectButton = (Button) findViewById(R.id.plus_disconnect_button);
        disconnectButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                revokeAccess();
            }
        });
    }

    @Override
    protected void onPlusClientBlockingUI(boolean show) {
        showProgress(show);
    }

    @Override
    protected void updateConnectButtonState() {
        //TODO: tr
        boolean connected =getPlusClient().isConnected();

        mSignOutButtons.setVisibility(connected ? View.VISIBLE : View.GONE);
        mPlusSignInButton.setVisibility(connected ? View.GONE : View.VISIBLE);
        mEmailLoginFormView.setVisibility(connected ? View.GONE : View.VISIBLE);
    }

    @Override
    protected void onPlusClientRevokeAccess() {
        // TODO: Access to the user's G+ account has been revoked.  Per the developer terms, delete
        // any stored user data here.
    }

    @Override
    protected void onPlusClientSignOut() {

    }

    /**
     * Check if the device supports Google Play Services.  It's best
     * practice to check first rather than handling this as an error case.
     *
     * @return whether the device supports Google Play Services
     */
    private boolean supportsGooglePlayServices() {
        return GooglePlayServicesUtil.isGooglePlayServicesAvailable(this) ==
                ConnectionResult.SUCCESS;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return new CursorLoader(this,
                // Retrieve data rows for the device user's 'profile' contact.
                Uri.withAppendedPath(ContactsContract.Profile.CONTENT_URI,
                        ContactsContract.Contacts.Data.CONTENT_DIRECTORY), ProfileQuery.PROJECTION,

                // Select only email addresses.
                ContactsContract.Contacts.Data.MIMETYPE +
                        " = ?", new String[]{ContactsContract.CommonDataKinds.Email
                .CONTENT_ITEM_TYPE},

                // Show primary email addresses first. Note that there won't be
                // a primary email address if the user hasn't specified one.
                ContactsContract.Contacts.Data.IS_PRIMARY + " DESC");
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        List<String> emails = new ArrayList<String>();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            emails.add(cursor.getString(ProfileQuery.ADDRESS));
            cursor.moveToNext();
        }

        addEmailsToAutoComplete(emails);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {

    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.d(TAG,"Coonnect suspend");
    }

    private interface ProfileQuery {
        String[] PROJECTION = {
                ContactsContract.CommonDataKinds.Email.ADDRESS,
                ContactsContract.CommonDataKinds.Email.IS_PRIMARY,
        };

        int ADDRESS = 0;
        int IS_PRIMARY = 1;
    }

    /**
     * Use an AsyncTask to fetch the user's email addresses on a background thread, and update
     * the email text field with results on the main UI thread.
     */
    class SetupEmailAutoCompleteTask extends AsyncTask<Void, Void, List<String>> {

        @Override
        protected List<String> doInBackground(Void... voids) {
            ArrayList<String> emailAddressCollection = new ArrayList<String>();

            // Get all emails from the user's contacts and copy them to a list.
            ContentResolver cr = getContentResolver();
            Cursor emailCur = cr.query(ContactsContract.CommonDataKinds.Email.CONTENT_URI, null,
                    null, null, null);
            while (emailCur.moveToNext()) {
                String email = emailCur.getString(emailCur.getColumnIndex(ContactsContract
                        .CommonDataKinds.Email.DATA));
                emailAddressCollection.add(email);
            }
            emailCur.close();

            return emailAddressCollection;
        }

        @Override
        protected void onPostExecute(List<String> emailAddressCollection) {
            addEmailsToAutoComplete(emailAddressCollection);
        }
    }

    private void addEmailsToAutoComplete(List<String> emailAddressCollection) {
        //Create adapter to tell the AutoCompleteTextView what to show in its dropdown list.
        ArrayAdapter<String> adapter =
                new ArrayAdapter<String>(LoginActivity.this,
                        android.R.layout.simple_dropdown_item_1line, emailAddressCollection);

        mEmailView.setAdapter(adapter);
    }

    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

        private final String mEmail;
        private final String mPassword;
        private  String mResult = "ZER0";

        UserLoginTask(String email, String password) {
           // db = users.getWritableDatabase();

           // db.close();
            mEmail = email;
            mPassword = password;
            //String sqlLogin = "SELECT * FROM users WHERE email ='" +mEmail+ "'" ;

            //cr = db.execSQL(sqlLogin);
          //  cr = db.query(users.TABLE,new String[]{users.C_NICKNAME,users.C_PASSWORD},)
            //cr = db.query(users.TABLE,null,null,null,null,null,null);
            //startManagingCursor(cr);
        }

        @Override
        protected Boolean doInBackground(Void... params) {
//            boolean fuck = true;
//            if (fuck)
//                return true;
            // TODO: attempt authentication against a network service.

            // Simulate network access.
            //ResultSet rs = DBHandler.GET.getAll();


                ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
                postParameters.add(new BasicNameValuePair("mode","login"));
                postParameters.add(new BasicNameValuePair("email",mEmail));
                postParameters.add(new BasicNameValuePair("password", mPassword));
                String response = null;
                try {
                    response = CustomHttpClient.executeHttpPost("http://bastion.ga/check.php", postParameters);  //Enetr Your remote PHP,ASP, Servlet file link
                    String res=response.toString();
                    int i = res.indexOf('\n');
                    res = res.substring(0,i);
                    //res= res.replaceAll("\\s+","");

                    if(!res.equals("NA"))
                    {
                        mResult = res;
                        return true;
                    }


//                    while (rs.next()){
//
//
//    //                String name =  cr.getString(cr.getColumnIndex(users.C_EMAIL));
//    //                String pass =  cr.getString(cr.getColumnIndex(users.C_PASSWORD));
//                    String name = null;
//                    String pass = null;
//
//                        name = rs.getString("name");
//                        pass =  rs.getString("password");
//
//
//                    if(name.equals(mEmail))
//                    {
//                        Log.i(TAG,mEmail+" SUCSsES LOGINED");
//                        return pass.equals(mPassword);
//                    }
//                }
                } catch (Exception e) {
                    e.printStackTrace();
                }


                Log.d(TAG,"Registration new USER");
                String nick = mEmail.substring(0,mEmail.indexOf('@'));
                postParameters.add(new BasicNameValuePair("mode","registration"));
               // postParameters.add(new BasicNameValuePair("email",mEmail));
            //    postParameters.add(new BasicNameValuePair("password", mPassword));
               // String response = null;
                postParameters.add(new BasicNameValuePair("nickname",nick));
                postParameters.add(new BasicNameValuePair("result",mResult));
                try {
                    response = CustomHttpClient.executeHttpPost("http://bastion.ga/check.php", postParameters);  //Enetr Your remote PHP,ASP, Servlet file link
                    String res=response.toString();
                    int i = res.indexOf('\n');
                    res = res.substring(0,i);
                    //res= res.replaceAll("\\s+","");

                    if(res.equals("1"))
                        Log.d(TAG,"NEW USER: "+mEmail+mPassword+"Are registred");
                    else
                        Log.d(TAG,"Error in REGISTERING");
    //                    while (rs.next()){
    //
    //
    //    //                String name =  cr.getString(cr.getColumnIndex(users.C_EMAIL));
    //    //                String pass =  cr.getString(cr.getColumnIndex(users.C_PASSWORD));
    //                    String name = null;
    //                    String pass = null;
    //
    //                        name = rs.getString("name");
    //                        pass =  rs.getString("password");
    //
    //
    //                    if(name.equals(mEmail))
    //                    {
    //                        Log.i(TAG,mEmail+" SUCSsES LOGINED");
    //                        return pass.equals(mPassword);
    //                    }
    //                }
                } catch (Exception e) {
                    e.printStackTrace();
                }




//            ContentValues cv = new ContentValues();
//            cv.clear();
//            String nick = mEmail.substring(0,mEmail.indexOf('@'));
//            cv.put(users.C_NICKNAME,nick);
//            cv.put(users.C_EMAIL,mEmail);
//            cv.put (users.C_PASSWORD,mPassword);
//            cv.put (users.C_RESULTS,"0NU");
//            db.insertOrThrow(users.TABLE,null,cv);
          //  DBHandler.GET.insertUsers(nick,mEmail,mPassword,"ZERO");



            Log.d(TAG,"NEW USER: "+mEmail+mPassword+"Are registred");

            //db.close();
            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;
            showProgress(false);

            if (success) {
               //TODO:
                Log.i(TAG,"Login comlited");

                Intent mainIntent = new Intent(LoginActivity.this,MainActivity.class);
                mainIntent.putExtra("EMAIL",mEmail);
                mainIntent.putExtra("PASSWORD",mPassword);
                mainIntent.putExtra("RESULT",mResult);
                LoginActivity.this.startActivity(mainIntent);
                LoginActivity.this.finish();
            } else {
                mPasswordView.setError(getString(R.string.error_incorrect_password));
                mPasswordView.requestFocus();
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            //db.close();
            showProgress(false);
        }
    }
}



