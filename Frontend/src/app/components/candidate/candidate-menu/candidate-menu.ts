import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { NavigationEnd, Router, RouterLink, RouterOutlet } from '@angular/router';
import { MenuItem } from 'primeng/api';
import { Menubar } from 'primeng/menubar';
import { Menu } from 'primeng/menu';
import { ButtonModule } from 'primeng/button';

@Component({
  selector: 'app-candidate-menu',
  imports: [Menubar, RouterOutlet, CommonModule, Menu, ButtonModule, RouterLink],
  templateUrl: './candidate-menu.html',
  styleUrl: './candidate-menu.css'
})
export class CandidateMenu {

  activeItem: string = '';

  constructor(public router: Router) { }

  items: MenuItem[] | undefined;
  endItems: MenuItem[] | undefined;

  ngOnInit() {
    this.activeItem = window.location.pathname;
    this.items = [
      {
        label: 'Home',
        icon: 'pi pi-home',
        routerLink: '/candidate/home',
      },
      {
        label: 'Practice with AI Interview',
        icon: 'pi pi-microchip-ai',
        routerLink: '/candidate/AiInterview'
      }
    ];

    this.endItems = [
      {
        label: 'Profile',
        icon: 'pi pi-user',
        items: [
          {
            label: 'Overview',
            icon: 'pi pi-table'
          },
          {
            label: 'Basic Info',
            icon: 'pi pi-info-circle'
          },
          {
            label: 'Education',
            icon: 'pi pi-book'
          },
          {
            label: 'Experience',
            icon: 'pi pi-briefcase'
          }
        ]
      }
    ];

    // update active item on route change
    this.router.events.subscribe(event => {
      if (event instanceof NavigationEnd) {
        let links = document.getElementsByClassName('p-menubar-item-link');
        let items = document.getElementsByClassName('p-menubar-item');
        for(let i = 0; i < links.length; i++){
          items[i].classList.remove('p-focus');
          links[i].classList.remove('active');
        }
        this.activeItem = event.urlAfterRedirects;
      }
    });
  }

  isActive(item: MenuItem): boolean {
    return item.routerLink === this.activeItem;
  }
}
