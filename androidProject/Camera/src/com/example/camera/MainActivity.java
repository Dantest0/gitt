package com.example.camera;

import java.io.File;
import java.io.FileOutputStream;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.ImageFormat;
import android.hardware.Camera;
import android.hardware.Camera.AutoFocusCallback;
import android.hardware.Camera.PictureCallback;
import android.hardware.Camera.ShutterCallback;
import android.os.Bundle;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends Activity {
	Camera camera=null;
	SurfaceView sView=null;
	SurfaceHolder sHolder=null;
	
	int sWidth,sHeight;
	boolean isPreview=false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		WindowManager wm=getWindowManager();
		Display display=wm.getDefaultDisplay();
		DisplayMetrics metrics=new DisplayMetrics();
		display.getMetrics(metrics);
		sWidth=metrics.widthPixels;
		sHeight=metrics.heightPixels;
		sView=(SurfaceView)findViewById(R.id.sView);
		sView.setOnLongClickListener(new View.OnLongClickListener(){
			@Override
			public boolean onLongClick(View v){
				camera.autoFocus(autoFocus);
				Toast.makeText(MainActivity.this,"Long Click",Toast.LENGTH_SHORT).show();
				return true;
			}
		});
		sView.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View v){
				camera.autoFocus(autoFocus2);
				Toast.makeText(MainActivity.this,"SHORT Click",Toast.LENGTH_SHORT).show();
			}
		});
		
		sView.getHolder().setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		
		sHolder=sView.getHolder();
		sHolder.addCallback(new Callback(){

			@Override
			public void surfaceChanged(SurfaceHolder holder, int format,int width, int height) {
				
			}
			@Override
			public void surfaceCreated(SurfaceHolder holder) {
				initCamera();
			}
			@Override
			public void surfaceDestroyed(SurfaceHolder holder) {
				if(camera!=null){
					if(isPreview){
						camera.stopPreview();
					}
					camera.release();
					camera=null;
				}
			}
		});
	}
	
	
	private void initCamera(){
		if(!isPreview){
			camera=Camera.open(0);
			camera.setDisplayOrientation(90);
		}
		if(camera!=null&&!isPreview){
			try{
				Camera.Parameters par=camera.getParameters();
				par.setPreviewSize(sWidth, sHeight);
				par.setPreviewFpsRange(10,25);
				par.setPictureFormat(ImageFormat.JPEG);
				par.set("jpeg-quality",85);
				par.setPictureSize(sWidth, sHeight);
				camera.setPreviewDisplay(sHolder);
				camera.startPreview();
			}catch(Exception e){
				e.printStackTrace();
			}
			isPreview=true;
		}
	}
	
//	public void capture(View v){
//		
//	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			camera.autoFocus(autoFocus2);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	AutoFocusCallback autoFocus=new AutoFocusCallback(){
		@Override
		public void onAutoFocus(boolean success,Camera camera){
//			if(success){
//				camera.takePicture(new ShutterCallback(){
//					public void onShutter(){
//						
//					}
//				},new PictureCallback(){
//					public void onPictureTaken(byte[] data,Camera c){
//						
//					}
//				},myJpeg);
//			}
		}
	};
	AutoFocusCallback autoFocus2=new AutoFocusCallback(){
		@Override
		public void onAutoFocus(boolean success,Camera camera){
			if(success){
				camera.takePicture(new ShutterCallback(){
					public void onShutter(){
						
					}
				},new PictureCallback(){
					public void onPictureTaken(byte[] data,Camera c){
						
					}
				},myJpeg);
			}
		}
	};
	
	PictureCallback myJpeg=new PictureCallback(){
		@Override
		public void onPictureTaken(byte[] data,Camera camera){
			final Bitmap bm=BitmapFactory.decodeByteArray(data,0,data.length);
			View saveDialog=getLayoutInflater().inflate(R.layout.save,null);
			final EditText name=(EditText)saveDialog.findViewById(R.id.phone_name);
			final ImageView imageView=(ImageView)saveDialog.findViewById(R.id.show);
			imageView.setImageBitmap(bm);
			new AlertDialog.Builder(MainActivity.this).setView(saveDialog)
				.setPositiveButton("保存",new OnClickListener(){
					@Override
					public void onClick(DialogInterface dialog,int which){
						File file=new File(Environment.getExternalStorageDirectory(),name.getText().toString()+".jpeg");
						FileOutputStream fos=null;
						try{
							fos=new FileOutputStream(file);
							bm.compress(CompressFormat.JPEG,100,fos);
							Toast.makeText(MainActivity.this,"已经保存到"+file.toString(),Toast.LENGTH_LONG).show();
							fos.close();
						}catch(Exception e){
							e.printStackTrace();
						}
					}
				})
				.setNegativeButton("取消",null)
				.show();
			camera.stopPreview();
			camera.startPreview();
			isPreview=true;
		}
	};
}
