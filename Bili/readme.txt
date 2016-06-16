1.  适应app定义的apptheme主题，用 "?android:attr/xxx"

2.  继承Activity时，Activity有自带的actionbar也就是app界面最上层的标题栏；此时如果你想用自定义或者其他的toobar的话，你需要在你的系统主题里面去除掉actionbar，方法如下：
		<item name="windowActionBar">false</item>
        <item name="windowNoTitle">true</item>    
		
3. 