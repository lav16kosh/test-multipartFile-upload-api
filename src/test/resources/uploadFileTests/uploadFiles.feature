@test
Feature: Test upload file

Scenario: Test upload single file
Given Invoke request to add single file "requestBody1.json"
Then Verify response is 200
Then Verify the files count is 1 in the response


Scenario: Test upload multiple files
Given Invoke request to add multiple files <File>
|File|
|"Files/requestBody1.json"|
|"Files/requestBody2.json"|
|"Files/requestBody3.json"|
Then Verify response is 200
Then Verify the files count is 3 in the response