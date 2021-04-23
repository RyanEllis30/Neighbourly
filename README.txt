GIT Bash is needed to be installed to use this.
Create a folder called Neighbourly
Right click it and click GIT BASH here
type "git init"  # Initialises git repository
type "git remote add origin https://github.com/RyanEllis30/Neighbourly.git"  # Links your new repository to the main one
type "git pull origin main"  # Pulls from the main repository
Now you have the repo setup on your computer, you can edit and do everything you want.

To get the latest data you need to do: 
"git pull origin main"

If you want to create a seperate branch to not mess with the main repo:
"git checkout -b 'new name'"
*This 'new name' would be the name of the branch, the main branch is called 'main'*

If you want to upload data for everyone else to then use:
"git add 'file name'" For example:
git add README.txt
If there are multiple files you can just type "git add ." However this is bad practice and you should commit each individual file as they are individual changes
"git commit -m "commit message"" (You need those quotation marks around the commit message) For example
git commit "Made a new branch for UI"
"git push origin 'branch name'"
*Branch name will be main unless you create your own branch*
*By pushing to main you could cause errors for other people by overwriting files so always fetch the latest data before pushing


