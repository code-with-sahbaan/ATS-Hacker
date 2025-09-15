import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { MenuItem } from 'primeng/api';
import { Menubar } from 'primeng/menubar';
import { Menu } from 'primeng/menu';
import { ButtonModule } from 'primeng/button';

@Component({
  selector: 'app-candidate-menu',
  imports: [Menubar, RouterOutlet, CommonModule, Menu, ButtonModule],
  templateUrl: './candidate-menu.html',
  styleUrl: './candidate-menu.css'
})
export class CandidateMenu {
  items: MenuItem[] | undefined;

  endItems: MenuItem[] | undefined;

  ngOnInit() {
    this.items = [
      {
        label: 'Home',
        icon: 'pi pi-home',
        // command: () => {
        //     this.router.navigate(['/installation']);
        // }
      },
      {
        label: 'Features',
        icon: 'pi pi-star'
      },
      {
        label: 'Contact',
        icon: 'pi pi-envelope'
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
    ]
  }
}
