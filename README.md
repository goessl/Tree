# Tree

TreeNode class used to generate & store tree structures.
Every node stores a reference to its parent node, children nodes and its
data.
Every node is also a tree consisting of itself and all of its children.

## Usage

A new node/tree can be constructed with the data it should store.
To every node a child can be added with the add method.
With the getter methods you can get the parent, children and data of every
node.
Also a whole tree can be generated recursively with the constructor or add
method by giving it a growth function. It takes the data of a node and
should return data for its children. This way a complex tree can be
generated with a single function.

## Getting Started

Simply download this repository and add it to your project as a new package!
Done!

## License (MIT)

MIT License

Copyright (c) 2019 Sebastian Gössl

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
