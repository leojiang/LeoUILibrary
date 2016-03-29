package com.example.blurtest.draglayouttomenu;

public interface Callback {
	void onBefore();

	boolean onRun();

	void onAfter(boolean b);
}
