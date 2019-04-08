'use strict';

var messageInput = document.querySelector('#message');
var messageArea = document.querySelector('#messageArea');
var userPic = document.querySelector('#userPicName');

var stompClient = null;

function connect(event) {
    var socket = new SockJS('/ws');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, onConnected, onError);
    event.preventDefault();
}


function onConnected() {
    // Subscribe to the Public Topic
    stompClient.subscribe('/topic/public/' + chatId, onMessageReceived);

    // Tell your username to the server
    stompClient.send("/app/chat.addUser/" + chatId,
        {},
        JSON.stringify({sender: currentUsername})
    )
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
        userPicName = "/imgUserPic/" + userPic.value;
    } else {
        userPicName = "http://localhost:8080/static/images/title1.png"
    }
    if(messageContent && stompClient) {
        var chatMessage = {
            sender: currentUsername,
            senderId: userId,
            content: messageInput.value,
            userPicName: userPicName
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
        messageElement.classList.add("text-left");
    } else {
        messageElement.classList.add("text-right");
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
    var avatar = document.createElement('img');
    avatar.src = message.userPicName;
    avatar.width = 34;
    avatar.height = 34;
    avatar.classList.add('border-secondary');
    avatar.classList.add('border');
    avatar.classList.add('mx-2');
    avatar.classList.add('rounded-circle');
    return avatar; 
}

window.onload = connect
messageForm.addEventListener('submit', sendMessage, true)
