
import { Component, computed, OnInit, signal } from '@angular/core';
import { Book, BOOK_DIFFICULTY, BOOK_TYPES, BookDifficulty, BookType } from '../../shared/models/book.model';
import { BookService } from '../../core/services/book.service';
import { BookTagsPipe } from '../../shared/pipes/book-tags.pipe';
import { MatIconModule } from '@angular/material/icon';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [BookTagsPipe, MatIconModule],
  templateUrl: './home.component.html',
  styleUrl: './home.component.scss',
})
export class HomeComponent implements OnInit{
    books = signal<Book[]>([]);
    search = signal('');
    selectedTags = signal<BookType[] | null>(null);
    selectedDifficulty = signal<BookDifficulty | null>(null);
    tags = BOOK_TYPES;
    difficulties = BOOK_DIFFICULTY;

    constructor(private booService: BookService){}

    ngOnInit(): void {
      this.booService.getBooks().subscribe((books)=>(this.books.set(books)));
    }

    filterBooks = computed(() => {
      const value = this.search().toLowerCase().trim();
      const rawTags = this.selectedTags();
      const tags = rawTags && rawTags.length > 0 ? rawTags : null;
      const tagDif = this.selectedDifficulty();

      if(!value && !tags && !tagDif){
        return this.books();
      }

      return this.books().filter(book => {
        const matchSearch = !value || book.title.toLowerCase().includes(value) ||
          book.author.toLowerCase().includes(value);

        const matchTag = !tags || book.types.some(tag => tags.includes(tag));

        const matchDifficulty = !tagDif || book.difficulty === tagDif;
        
        return matchSearch && matchTag && matchDifficulty;
      });
    });


    onSearch(event: Event): void{
      const value = (event.target as HTMLInputElement).value;
      this.search.set(value);
    }

    chooseType(tag: BookType): void{
      if(this.selectedTags()?.includes(tag)){
        this.selectedTags.set(this.selectedTags()!.filter(dif => tag !== dif));
      } else {
        this.selectedTags.set([... (this.selectedTags() ?? []), tag]);
      }
    }

    chooseDifficulty(tag: BookDifficulty): void{
      if(tag === this.selectedDifficulty()){
        this.selectedDifficulty.set(null);
      } else {
        this.selectedDifficulty.set(tag);
      }
    }

}
