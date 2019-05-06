'use strict';

var messageInput = document.querySelector('#message');
var messageArea = document.querySelector('#messageArea');
var userPic = document.querySelector('#userPicName');
var chatIsActive = document.querySelector('#isChatActive');

var stompClient = null;

function connect(event) {
    var socket = new SockJS('/ws');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, onConnected, onError);
    event.preventDefault();
}


function onConnected() {
    if(chatIsActive.value == true) {
        // Subscribe to the Public Topic
        stompClient.subscribe('/topic/public/' + chatId, onMessageReceived);
    }
    messageArea.scrollTop = messageArea.scrollHeight;
}


function onError(error) {
    connectingElement.textContent = 'Could not connect to WebSocket server. Please refresh this page to try again!';
    connectingElement.style.color = 'red';
}


function sendMessage(event) {
    var messageContent = messageInput.value.trim();
    var userPicName;
    if(userPic.value != "") {
        userPicName = "/imgUserPic/" + currentUsername + "/" + userPic.value;
    } else {
        userPicName = "http://localhost:8080/static/images/defaultUserPic.png"
    }
    if(messageContent && stompClient) {
        var chatMessage = {
            sender: currentUsername,
            senderId: userId,
            content: messageInput.value,
            userPicName: userPicName,
            messageDate: new Date()
        };
        stompClient.send("/app/chat.sendMessage/" + chatId, {}, JSON.stringify(chatMessage));
        messageInput.value = '';
        userPicName = '';
    }
    event.preventDefault();
}


function onMessageReceived(payload) {

    var message = JSON.parse(payload.body);
    var messageText = document.createTextNode(message.content);
    var avatar = setAvatar(message);
    
    var messageElement = document.createElement('li');


    if(message.sender == currentUsername) {
        messageElement.classList.add('text-left');
    } else {
        messageElement.classList.add('text-right');
    }

    messageElement.classList.add('list-group-item');
    messageElement.classList.add('mt-2');

    if(message.sender == currentUsername) {
        messageElement.appendChild(avatar); 
    }
    messageElement.appendChild(messageText);

     if(message.sender != currentUsername) {
        messageElement.appendChild(avatar); 
    }

    messageArea.appendChild(messageElement);
    messageArea.scrollTop = messageArea.scrollHeight;
}

function setAvatar(message) {
    var userPic = document.createElement('img');
    userPic.src = message.userPicName;
    userPic.width = 34;
    userPic.height = 34;
    userPic.classList.add('border-secondary');
    userPic.classList.add('border');
    userPic.classList.add('ml-1');
    userPic.classList.add('mr-2');
    userPic.classList.add('rounded-circle');
    var avatar = document.createElement('a');
    avatar.href = "/" + message.senderId + "/profile";
    avatar.appendChild(userPic);
    return avatar; 
}

window.onload = connect
messageForm.addEventListener('submit', sendMessage, true)
