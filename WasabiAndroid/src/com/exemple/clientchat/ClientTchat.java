package com.exemple.clientchat;

import com.exemple.clientchat.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.KeyEvent;
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
	
	/**
	 * used to know the current layout and to switch between the current layout
	 * and the precedent one
	 */
	enum ListLayout {
		MainLayout, CreateUserLayout, ConnectUserLayout, ListContatLayout, 
		TchatLayout	};
	
	ListLayout currentLayout; 
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        currentLayout = ListLayout.MainLayout;
    }
    
    /**
	 * when destroying the activity
	 */
	@Override
	public void onDestroy() {
		super.onDestroy();
		this.finish();
	}
    
    /**
     * create user menu
     */
    public void createMenu(View btn){
    	setContentView(R.layout.create);
    	currentLayout = ListLayout.CreateUserLayout; 
    	EditText login = (EditText)findViewById(R.id.loginText);
    	EditText password = (EditText)findViewById(R.id.passText);
    	EditText passwordConf = (EditText)findViewById(R.id.passConfText);
    	
    }
    
    /**
     * authenticate user menu
     * @param btn
     */
    public void loginMenu(View btn) {
    	setContentView(R.layout.authent);
    	currentLayout= ListLayout.ConnectUserLayout;
    	
    }
    
    /**
     * get the client list in the ListView  
     * @param btn
     */
    public void getClientList (View btn){
    	setContentView (R.layout.list);
    	currentLayout = ListLayout.ListContactLayout;
    	
    	// an example wich populate our list with these names below
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
    	currentLayout = ListLayout.TchatLayout;
    	
    	TextView clientNameLbl = (TextView)findViewById(R.id.clientNameLbl);
    	clientNameLbl.setText(clientName);
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
    
    @Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			changeLayout();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
    
    /**
	 * to update the new layout to show 
	 */
	public void changeLayout() {

		switch (currentLayout) {
			case MainLayout: {
				showAlert("Quit Wasabi, Are you sure?", currentLayout);
				break;
			}
			case ConnectUserLayout:
			case CreateUserLayout: {
				setContentView(R.layout.main);
				currentLayout = ListLayout.MainLayout;
				break;
			}
			case ListContatLayout: {
				showAlert("Disconnect user?", currentLayout);
				break;
			}
			case TchatLayout: {
				setContentView(R.layout.list);
				currentLayout = ListLayout.ListContatLayout;
				break;
			}
		}
	}
	
	/**
	 * to show message alert 
	 * @param msg
	 */
	public void showAlert(String msg, final ListLayout layout) {
		new AlertDialog.Builder(this).setTitle("Attention!").setMessage(msg)
				.setPositiveButton("OK", new DialogInterface.OnClickListener() {
					//@Override
					public void onClick(DialogInterface dialog, int which) {
						switch (layout) {
						case MainLayout: {
							onDestroy();
							break;
						}
						
						case ListContatLayout: {
							setContentView(R.layout.main);
							currentLayout = ListLayout.MainLayout;
							break;
						}
						default: {
							break;
						}
						}
					}
				}).show();
	}
}