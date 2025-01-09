// schedule.js
// 유저의 전체 일정 확인하기---------------------
async function getUserSchedules(userId) {
    const response = await fetch(`/schedule/${userId}`);
    const schedules = await response.json();
    // var eventDate = new Date(event.walkDateIso);
    return schedules.map(schedule => ({
        title: schedule.schedulename,
        start: new Date(schedule.walkDateIso),  // LocalDate를 JavaScript Date 객체로 변환
        extendedProps: {
            day: schedule.walkDate,  // 날짜 그대로 사용
            time: schedule.walkTime,
            place: schedule.walkPlace,
            // matching: schedule.matching
        }
    }));
}

//(모달) 상세 정보---------------------------
function displayEventDetails(eventDetails) {

    const modalTitle = document.getElementById('modalTitle');
    const modalDate = document.getElementById('modalDate');
    const modalTime = document.getElementById('modalTime');
    const modalPlace = document.getElementById('modalPlace');


    modalTitle.innerText = eventDetails.schedulename;// 일정 제목
    modalDate.innerText = formatDate(eventDetails.walkDate) || 'N/A';  // 일정 날짜 형식 수정
    modalTime.innerText = formatTime(eventDetails.walkTime) || 'N/A';  // 시간 정보
    modalPlace.innerText = eventDetails.place || 'N/A';    // 장소 정보

    // 모달 띄우기
    document.getElementById('scheduleModal').style.display = 'flex';
}

//---------------------------------------------
// 시간 형식 변환 함수
function formatTime(walkTime) {
    if (!walkTime) return 'N/A';
    // walkTime이 문자열인지 확인하고, 문자열이 아니면 강제로 문자열로 변환
    if (typeof walkTime !== 'string') {
        walkTime = String(walkTime);  // 문자열로 변환
    }
    const timeParts = walkTime.split(',');
    const hours = timeParts[0].padStart(2, '0');  // 시간 앞에 0 추가
    const minutes = timeParts[1].padStart(2, '0');  // 분 앞에 0 추가
    return `${hours}시 ${minutes}분`;
}

// 날짜 형식 변환 함수
function formatDate(date) {
    if (!(date instanceof Date) || isNaN(date)) return 'N/A';  // 유효한 날짜가 아닐 경우 'N/A'

    // 날짜를 "yyyy/mm/dd" 형식으로 변환
    const year = date.getFullYear();
    const month = (date.getMonth() + 1).toString().padStart(2, '0');  // 월을 두 자리로 맞춤
    const day = date.getDate().toString().padStart(2, '0');  // 일을 두 자리로 맞춤

    return `${year}년 ${month}월 ${day}일`;  // "yyyy-mm-dd" 형식으로 반환

}