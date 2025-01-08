const apiUrl = '/schedules';


// 일정 전체 목록 조회
async function getSchedules({ page, size }) {
    try {
        const response = await axios.get(`${apiUrl}`, { params: { page, size } });
        return response.data; // 일정 목록 반환
    } catch (error) {
        if (error.response) {
            console.error('서버 응답ㅡ오류:', error.response.data.message || 'Unknown server error');
        } else if (error.request) {
            console.error('요청이 전송되었지만 응닶 없음');
        } else {
            console.error('기타 에러:', error.message);
        }
        throw error;
    }
}

// 일정 추가
async function addSchedule(schedule) {
    try {
        const response = await axios.post(`${apiUrl}`, schedule);
        return response.data; // 추가된 일정 ID 반환
    } catch (error) {
        console.error('Failed to add schedule:', error);
        throw error;
    }
}

// 일정 조회 (단일 일정)
async function getScheduleById(scheduleId) {
    try {
        const response = await axios.get(`\`${apiUrl}\`${scheduleId}`);
        return response.data; // 일정 데이터 반환
    } catch (error) {
        console.error('Failed to fetch schedule:', error);
        throw error;
    }
}

// 일정 수정
async function updateSchedule(schedule) {
    try {
        const response = await axios.put(`\`${apiUrl}\`${schedule.id}`, schedule);
        return response.data; // 수정된 일정 데이터 반환
    } catch (error) {
        console.error('Failed to update schedule:', error);
        throw error;
    }
}

// 일정 삭제
async function deleteSchedule(scheduleId) {
    try {
        const response = await axios.delete(`\`${apiUrl}\`${scheduleId}`);
        return response.data; // 삭제 성공 여부 반환
    } catch (error) {
        console.error('Failed to delete schedule:', error);
        throw error;
    }
}
