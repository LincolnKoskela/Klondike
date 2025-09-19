# Klondike
Using Java to create a classic card game of Klondike Solitaire

In the game of Klondike the board is split into 4 main sections. 
You have the Stock pile, Waste pile, the Foundation pile(s), and the Tableau pile(s)

The Tableau pile is split into 7 sections across the board and is where the main action is happening. 
This is where you're sorting the cards by alt color and rank, drawing from the Stock pile. 
For the initial deck distribution when the game starts, the shuffled cards neatly placed into the 
7 tableau piles. Starting in the 1st pile, most left, with 1 card face up. The adjacent pile to the right
now has 2 cards, with only top card face up. All the way through the tableau until the last section increasing
by one card, with only top card face up.

The Stock pile is where cards are drawn from to sort cards on the Tableau. After the initial deck distribution 
from the tableau, the rest of the cards will go to the stock pile. From here you draw a card to see if you can play 
it on the tableau, or into the foundation if you're lucky:), or if you need to place in the waste pile. 
Once you're out of cards in the stock, you 'recycle' the cards from waste back into stock. Game on.

The Waste pile is where cards are placed if they're not played into the tableau or into the foundation.
Cards here can be 'recycled' back into the Stock when the Stock runs out of cards

Then theres where all the magic happens. The Foundation pile is where you sort the cards by suit and rank. 
Spades get their own along with Clubs, Hearts, and Diamonds. The way to sort these cards is from Ace to 
King. You can also pull cards from the sorted foundation to help sort the tableau. That's where things get 
tough and you have to see it before you miss the opportunity. 

Once the foundation is sorted of all 4 Suits by their ranks, you've officially won the game!

This project is currently a work in progress. I contribute about an hour a day. 
This is the first real personal project im doing. It's teaching me
alot about OOP and encapsulation and algorithms. And I love playing this game so why not make 
one of my own!


Right now I'm tackling the Stock.java class. 
