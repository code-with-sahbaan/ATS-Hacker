import { Routes } from '@angular/router';
import { SignIn } from './pages/sign-in/sign-in';
import { ManageProfile } from './pages/manage-profile/manage-profile';
import { Redirect } from './pages/redirect/redirect';
import { CandidateMenu } from './components/candidate/candidate-menu/candidate-menu';
import { Profile } from './pages/profile/profile';
import { Home } from './pages/candidate/home/home';
import { Home as RecruiterHome } from './pages/recruiter/home/home';
import { RecruiterMenu } from './components/recruiter/recruiter-menu/recruiter-menu';
import { PostJob } from './pages/recruiter/post-job/post-job';

export const routes: Routes = [
    {
        path:'',
        component: SignIn,
        data: { title: 'Welcome' }
    },
    {
        path:'manageProfile',
        component: ManageProfile,
        data: { title: 'Manage Profile' }
    },
    {
        path:'authorize',
        component: Redirect,
        data: { title: 'Authorizing User' }
    },

    // Candidate Menus
    { path: 'candidate', redirectTo: 'candidate/home' },
    {
        path: 'candidate',
        component: CandidateMenu,
        children: [
            {
                path: 'home',
                component: Home,
                data: { title: 'Home' }
            },
            {
                path: 'profile',
                component: Profile,
                data: { title: 'Profile' }
            }
        ]
    },

    // Recruiter Menus
    { path: 'recruiter', redirectTo: 'recruiter/home' },
    {
        path: 'recruiter',
        component: RecruiterMenu,
        children: [
            {
                path: 'home',
                component: RecruiterHome,
                data: { title: 'Home' }
            },
            {
                path: 'post-job',
                component: PostJob,
                data: { title: 'Post Job' }
            },
            {
                path: 'profile',
                component: Profile,
                data: { title: 'Profile' }
            }
        ]
    }

];
