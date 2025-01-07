//채팅방 추가
async function addRoom(roomRegisterObj) {
    const response = await axios.post(`/matchingRoom/roomRegister`, roomRegisterObj);
    return response.data;
}
//채팅방 업데이트
async function UpdateRoom(roomInfoObj){
    const response = await axios.put(`/matchingRoom/${roomInfoObj.roomId}`,roomInfoObj)
    return response.data
}

//채팅방 삭제
async function deleteRoom(roomId) {
    const response = await axios.delete(`/matchingRoom/${roomId}`);  // Send roomId in the URL
    return response.data;
}
//채팅방 나가기
async function updateAndDelete(exitObj){
    console.log(exitObj);
    const response = await axios.post(`/matchingRoom/roomUAD`, exitObj);
    return response.data
}
//채팅 조회
async function getChatList(roomId){
    const result = await axios.get(`/matchingRoom/chatList/${roomId}`);
    return result.data
}
//채팅 작성
async function addMessage(messageObj){
    const result = await axios.post('/matchingRoom/messageRegister',messageObj)
    return result.data
}
//채팅 삭제
async function deleteMessage(messageId){
    console.log("삭제할 메시지 번호"+ messageId)
    const result = await  axios.delete(`/matchingRoom/${messageId}`);
    return result.data
}







