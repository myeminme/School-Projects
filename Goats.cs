using System;
using System.Collections.Generic;
using System.Reflection;
using System.Security.Cryptography.X509Certificates;
using Avalonia;
using Avalonia.Controls;
using Avalonia.Interactivity;
using Avalonia.Media;
using Avalonia.Media.Imaging;
using Avalonia.Metadata;
using Microsoft.Extensions.FileProviders;

public class Goats
{
    public Window win;
    private Image[] images = new Image[9];
    private Grid grid;
    private List<(string name, int oldColumn, int newColumn)> moveHistory = new List<(string name, int oldColumn, int newColumn)>();
    private char[] allPos;
    public Goats()

    {

        win = new Window
        {
            Width = 1000,
            Height = 100,
            Title = "GridWindow",
            WindowStartupLocation = WindowStartupLocation.CenterScreen,
        };

        grid = new Grid
        {
            RowDefinitions = RowDefinitions.Parse("*"),
            ColumnDefinitions = ColumnDefinitions.Parse("*,*,*,*,*,*,*,*,*,*"),
            Background = Brushes.Orange,
            ShowGridLines = true,
        };

        //INITIALIZING GOAT POS
        allPos = ['R', 'R', 'R', 'R', '-', 'L', 'L', 'L', 'L'];

        //IMAGE SETUP
        Bitmap goatLeft = GetBitmap("GoatLeft.png");
        Bitmap goatRight = GetBitmap("GoatRight.png");
        Bitmap undopic = GetBitmap("Undo.png");

        //RIGHT GOATS
        for (int i = 0; i < 4; i++)
        {
            images[i] = new Image
            {
                Source = goatRight,
                Name = $"goatRight{i}"
            };

            Grid.SetColumn(images[i], i);
            grid.Children.Add(images[i]);
            images[i].PointerPressed += Jump;
        }
        //LEFT GOATS
        for (int i = 5; i < 9; i++)
        {
            images[i] = new Image
            {
                Source = goatLeft,
                Name = $"goatLeft{i}"
            };

            Grid.SetColumn(images[i], i);
            grid.Children.Add(images[i]);
            images[i].PointerPressed += Jump;
        }
        //UNDO BUTTON
        Image undo = new Image
        {
            Source = undopic
        };
        undo.PointerPressed += UndoMove;
        undo.SetValue(Grid.ColumnProperty, 9);
        grid.Children.Add(undo);
        win.Content = grid;
    }

    void Jump(object sender, RoutedEventArgs e)
    {
        Image image = sender as Image;
        int column = image.GetValue(Grid.ColumnProperty);
        int index;

        char side = allPos[column];

        //Goats Moving
        if (side == 'R')
        {
            index = column + 1;

            // check jump possibility
            if (index <= 8 && allPos[index] == 'L')
            {
                index++;
            }

            // only move if landing spot is empty
            if (index <= 8 && allPos[index] == '-')
            {
                allPos[column] = '-';
                allPos[index] = 'R';
                moveHistory.Add((image.Name, column, index));
                image.SetValue(Grid.ColumnProperty, index);
            }
        }
        else if (side == 'L')
        {
            index = column - 1;

            // check jump possibility
            if (index >= 0 && allPos[index] == 'R')
            {
                index--;
            }

            // only move if landing spot is empty
            if (index >= 0 && allPos[index] == '-')
            {
                allPos[column] = '-';
                allPos[index] = 'L';
                moveHistory.Add((image.Name, column, index));
                image.SetValue(Grid.ColumnProperty, index);
            }
        }

    }

    // UNDO MOVE
    void UndoMove(object sender, RoutedEventArgs e)
    {
        if (moveHistory.Count > 0)
        {
            var lastMove = moveHistory[moveHistory.Count - 1];
            moveHistory.RemoveAt(moveHistory.Count - 1);

            //FINDING AND UPDATING IMAGES
            for (int i = 0; i < images.Length; i++)
            {
                if (images[i] != null && images[i].Name == lastMove.name)
                {
                    char side = allPos[lastMove.newColumn];
                    allPos[lastMove.oldColumn] = side;
                    allPos[lastMove.newColumn] = '-';

                    images[i].SetValue(Grid.ColumnProperty, lastMove.oldColumn);
                    break;
                }
            }
        }
    }

    Bitmap GetBitmap(string resourceName)
    {
        var embeddedProvider = new EmbeddedFileProvider(Assembly.GetExecutingAssembly());

        using (var reader = embeddedProvider.GetFileInfo(resourceName).CreateReadStream())
        {
            return new Bitmap(reader);
        }
    }
}
