# Setup

Copy and paste these files to your package  
In my case I am using IntelliJ and my package name is org.example  
  
![image](https://user-images.githubusercontent.com/73429081/228124918-37480cca-b5ea-48f5-af5d-9c3b10f7903d.png)  

Check your dependencies  
  
![image](https://user-images.githubusercontent.com/73429081/228125451-7fb17866-724b-43fc-bb46-baaeb5ffc742.png)  
  
I am using my local hadoop so use the following commands to start and check if its working  
start-all.sh  
jps  
  
Create input folder from the root  
  
![image](https://user-images.githubusercontent.com/73429081/228125984-afe55041-838e-4342-a2ce-da7347530e44.png)  
  
Make sure you have uploaded your required files into the input folder  
AFINN-en-165.txt (google and use the first result from github)    
![image](https://user-images.githubusercontent.com/73429081/228129600-5cf058a0-2ba7-4a71-ad56-7ade910ab4a1.png)  

stopwords.txt (google and look for the one from kaggle)    
![image](https://user-images.githubusercontent.com/73429081/228129505-281c70eb-36b5-4f66-a01e-df6ee06a8e5d.png)  

whatever csv file youre trying to process  
  
![image](https://user-images.githubusercontent.com/73429081/228126269-7c2afd7f-0e8e-4b32-b9d4-709cce0bb8d9.png)  
  
Double check that the data is in this format with the front blank header removed  
  
![image](https://user-images.githubusercontent.com/73429081/228126704-75fce0fb-2a3e-4223-9059-ae92d5968c27.png)  
  
# Execution

Click that in intelliJ  
  
![image](https://user-images.githubusercontent.com/73429081/228127103-ba3f01da-e3b5-4504-823a-8a407f09ab64.png)  
  
Clean then install  
  
![image](https://user-images.githubusercontent.com/73429081/228127341-f8644d90-0d39-45ff-a2ca-139eb374c37a.png)  
  
Ensure that you are in your project folder  
  
![image](https://user-images.githubusercontent.com/73429081/228127923-8ffd2b32-c93c-4bc7-bcc1-2ef33fe368a3.png)  

Type this into your termnial  
  
![image](https://user-images.githubusercontent.com/73429081/228127720-22693f64-c80c-4c41-8893-cd5d4ebcfff7.png)
  
Check your output  
  
![image](https://user-images.githubusercontent.com/73429081/228128355-62dd24d9-18af-4ea4-8cc3-984c02b2fe0f.png)  
  
![image](https://user-images.githubusercontent.com/73429081/228128429-fc98bfb5-6d00-4a14-a2a6-8b8cf16fb6ea.png)
  
Click on part-r-00000 to view/download results  
  
![image](https://user-images.githubusercontent.com/73429081/228128695-dc66e517-d187-4d88-88e6-e96edef55f07.png)  
  
![image](https://user-images.githubusercontent.com/73429081/228128782-402639de-2ec7-4fdd-971d-88358448c9b2.png)  





  


  

 
  




