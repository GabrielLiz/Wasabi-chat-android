package com.exemple.clienttchat;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class ClientTchat extends Activity {
	
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }
    
    /**
     * create user menu
     */
    public void createMenu(View btn){
    	setContentView(R.layout.create);
    }
    
    /**
     * authenticate user menu
     * @param btn
     */
    public void loginMenu(View btn) {
    	setContentView(R.layout.authent);
    }
    
    /**
     * get the client list in the ListView  
     * @param btn
     */
    public void getClientList (View btn){
    	setContentView (R.layout.list);
    	String [] clientList = new String[]{"alain", "toto", "tom", "jack"};
    	final ListView list = (ListView)findViewById(R.id.clientList);
    	list.setAdapter(new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, clientList));  
    	
    	list.setOnItemClickListener(new ListView.OnItemClickListener() {
			public void onItemClick(AdapterView<?> a, View v, int pos, long l) {
				tchatMenu(list.getItemAtPosition(pos).toString());
			}
		});
    	
    }
    
    /**
     * the principal window of tchat  
     * @param clientName
     */
    public void tchatMenu (String clientName){
    	setContentView(R.layout.tchat);
    	TextView clientNameLbl = (TextView)findViewById(R.id.clientNameLbl);
    	clientNameLbl.setText(clientName);
    	RelativeLayout tchatLay = (RelativeLayout)findViewById(R.id.tchatLayout);
    	tchatLay.bringToFront();

    }
    
    /**
     * the listener for send message button
     * @param btn
     */
    public void sendMsg(View btn){
    	//setContentView(R.layout.tchat);
    	EditText edit = (EditText)findViewById(R.id.tchatText);
    	ScrollView scroll = (ScrollView)findViewById(R.id.scrollView);
    	TextView messageTxt = (TextView)findViewById(R.id.tchatLbl);
    	String msg = edit.getText().toString();
    	messageTxt.append("\n");
    	messageTxt.append(msg);
    	edit.setText("");
    }
}