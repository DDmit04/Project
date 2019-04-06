'use strict';

var usernamePage = document.querySelector('#username-page');
var chatPage = document.querySelector('#chat-page');
var usernameForm = document.querySelector('#usernameForm');
var messageForm = document.querySelector('#messageForm');
var messageInput = document.querySelector('#message');
var messageArea = document.querySelector('#messageArea');
var connectingElement = document.querySelector('.connecting');

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
        JSON.stringify({sender: username})
    )
}


function onError(error) {
    connectingElement.textContent = 'Could not connect to WebSocket server. Please refresh this page to try again!';
    connectingElement.style.color = 'red';
}


function sendMessage(event) {
    var messageContent = messageInput.value.trim();

    if(messageContent && stompClient) {
        var chatMessage = {
            sender: username,
            content: messageInput.value,
        };

        stompClient.send("/app/chat.sendMessage/" + chatId, {}, JSON.stringify(chatMessage));
        messageInput.value = '';
    }
    event.preventDefault();
}


function onMessageReceived(payload) {
    var message = JSON.parse(payload.body);
    var messageElement = document.createElement('li');
    var avatar = document.createElement('img');
    avatar.src = "/imgUserPic/" + currentUserPic;
    avatar.width = 34;
    avatar.height = 34;
    avatar.classList.add('border border-secondary');
    avatar.classList.add('mr-2');
    if(message.sender == username) {
        messageElement.classList.add("text-left");
    } else {
        messageElement.classList.add("text-right");
    }
    messageElement.classList.add('list-group-item');
    messageElement.classList.add('mt-2');    

    var messageText = document.createTextNode(message.content);

    messageElement.appendChild(messageText);
    messageElement.appendChild(avatar);

    messageArea.appendChild(messageElement);
    messageArea.scrollTop = messageArea.scrollHeight;
}

window.onload = connect
messageForm.addEventListener('submit', sendMessage, true)
