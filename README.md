# Social Universe
## Contributors 
1. Yoo Sung Jang, 001, and yjang43@wisc.edu
2. Yong Seong Kim, 001, and ykim776@wisc.edu
3. Zan Zhang, 002, and zzhang786@wisc.edu
4. Charles Asiama, 001, and asiama@wisc.edu
5. Dana Ingram, 002, dolsen5@wisc.edu

## Overview
**Social Network** help UW-Madison alumni to connect one badger to another by searching an alumni and showing the path how the main user can contact to the alumni.
This program implemented _Graph_ and _Dijikstra's_ algorithm to display the shortest path to an alumni and used _JavaFX_ for a graphical interface part


## Tutorial
This is a tutorial on how to use the SocialNetwork application.

### Start program
The "SocialNetwork" program can be run on a unix system command line shell.
To start the program first navigate to the directory that the program is listed in your directory.
"/user\_directory/SocialNetwork/"
Enter the command "make jar" in command line
Enter the comand "make runjar" to start the program.

Add Friends and Relationships
You can add a user to the "SocialNetwork" program in two ways:
1: Click the load button and enter the directory path of the text file you want to load.
"/user\_directory/SocialNetwork/friends\_000.txt"
SocialNetwork will create(remove) users and their relationships specified in the text file.

2: Click the "Add User" button and enter the name of the user you want to add.
2a: To add a relationship click the "Add User" button again and enter another user.
2b: Click the "Add" button to add the new user to "SocialNetwork" to establish a relationship.

### Display Friends and Relationships
After users and freinds have been established "SocialNeWork" will display the relationships
between users. The user at center is currently the user displayed with his/her relationships
among different users. Click a user to display his/her name and double click a user to set
that user to center and display thats users friends.

NOTE: You can add a user who is not friends with the current center user.
Enter the name of the user who you want your current center user to be friends with.
If the user is in the social network a message will be displayed indicating whether the user is
friends with the current center user.  If they are not friends, click the "Add Button" to
create a relationship.

### Find User
Use the "Find" button to verify if a user exists in the social network.

### Center User Social Links
Click on the center user social links in upper right pane of "SocialNetwork" to go to the
user other social networks, "LinkedIn", "Instagram", "facebook".

### Save Current Network
To save the current "SocialNetwork" click the "Save" button and specify the directory you
want to save the current network.

### Exit Social Network
To exit "Social Network", click the "x" button located on the upper right side of the program.
You will get a display prompt indicating whether you want to save or exit the program without
saving.

![img not available](https://github.com/yjang43/SocialNetwork/blob/master/screenshot_1.png "program screenshot")

