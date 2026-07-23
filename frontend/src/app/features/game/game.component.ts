import { Component, computed, OnInit, signal } from '@angular/core';
import { Book, Section } from '../../shared/models/book.model';
import { MatIconModule } from '@angular/material/icon';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { BookService } from '../../core/services/book.service';


@Component({
  selector: 'app-game',
  standalone: true,
  imports: [MatIconModule, RouterLink],
  templateUrl: './game.component.html',
  styleUrl: './game.component.scss',
})
export class GameComponent implements OnInit{
    book = signal<Book | null>(null);
    section = signal<Section | null>(null);

    constructor(private route: ActivatedRoute, private bookService: BookService){}

    ngOnInit(): void {
        const id = Number(this.route.snapshot.paramMap.get('id'));
        this.bookService.getBooks().subscribe(books => {
            this.book.set(books.find(b => b.id === id) ?? null);
            this.section.set(this.book()?.sections.find(s => s.type === 'BEGIN') ?? null);
        });
    }


}
