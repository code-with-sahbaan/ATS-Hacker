import { Routes } from '@angular/router';
import { SignIn } from './pages/sign-in/sign-in';
import { ManageProfile } from './pages/manage-profile/manage-profile';
import { Redirect } from './pages/redirect/redirect';

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
    }

];
