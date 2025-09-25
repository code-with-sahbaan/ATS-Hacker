import { environment } from "../../environments/environment";

const APP_URL = environment.app_url;
export const MAX_FILE_SIZE = 26214400; // 25 MB
export const ROLE_CANDIDATE = "CANDIDATE";
export const ROLE_RECRUITER = "RECRUITER";

export function getJWTtoken() {
    const data = getCurrentUserData();
    return data.accessToken;
}

export function getCurrentUserData() {
    const data = localStorage.getItem('USER');
    if (data) {
        return JSON.parse(data);
    } else {
        logout();
    }
}

export function getHomePageRedirection(user: any) {
    if (user.role == ROLE_CANDIDATE) {
        return 'candidate';
    } else {
        return 'recruiter';
    }
}

export function logout() {
    localStorage.clear();
    window.location.href = APP_URL;
}

export function formatDateTime(dateStr: string) {
    const date = new Date(dateStr);

    return new Intl.DateTimeFormat('en-US', {
        month: '2-digit',
        day: '2-digit',
        year: 'numeric',
        hour: '2-digit',
        minute: '2-digit',
        hour12: true
    }).format(date).replace(',', '');
}