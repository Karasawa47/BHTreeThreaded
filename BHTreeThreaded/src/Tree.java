public class Tree
{
   private Particle part;
   private Octant oct;
   private Tree tree000;
   private Tree tree010;
   private Tree tree100;
   private Tree tree110;
   private Tree tree001;
   private Tree tree011;
   private Tree tree101;
   private Tree tree111;
   
   //end attributes
   
   
   public Tree(Octant o)
   {
      this.oct=o;
      this.part=null;
      this.tree000=null;
      this.tree010=null;
      this.tree100=null;
      this.tree110=null;
      this.tree001=null;
      this.tree011=null;
      this.tree101=null;
      this.tree111=null;
   }//end constructor
   
   //returns true if tree has one or no particle
   public boolean isNullTree(Tree t)
   {
      if(t.tree000==null && t.tree010==null && t.tree100==null && t.tree110==null 
         && t.tree001==null && t.tree011==null && t.tree101==null && t.tree111==null)
      {
         return true;
      }
      else
      {
         return false;
      }
   }//end is NullTree
   
   //recursion to add nodes and particles 
   public synchronized void addParticle(Particle p)
   {
      if(this.part==null)
      {
         this.part=p;
      }
      //jhgjhghjg
      else if (this.isNullTree(this)==false)
      {
         this.part=p.add(this.part,p);
         Octant northWestUpper = this.oct.octant000();
         if(p.isIn(northWestUpper))
         {
            if(this.tree000==null)
            {
               this.tree000= new Tree(northWestUpper);
            }
            tree000.addParticle(p);
         }
         else
         {
            Octant northEastUpper = this.oct.octant010();
            if(p.isIn(northEastUpper))
            {
               if(this.tree010==null)
               {
                  this.tree010= new Tree(northEastUpper);
               }
               tree010.addParticle(p);
            }
            else
            {
               Octant southWestUpper = this.oct.octant100();
               if(p.isIn(southWestUpper))
               {
                  if(this.tree100==null)
                  {
                     this.tree100= new Tree(southWestUpper);
                  }
                  tree100.addParticle(p);
               }
               else
               {
                  Octant southEastUpper = this.oct.octant110();
                  if(p.isIn(southEastUpper))
                  {
                     if(this.tree110==null)
                     {
                        this.tree110= new Tree(southEastUpper);
                     }
                     tree110.addParticle(p);
                  }
                  else
                  {
                     Octant northWestLower = this.oct.octant001();
                     if(p.isIn(northWestLower))
                     {
                        if(this.tree001==null)
                        {
                           this.tree001= new Tree(northWestLower);
                        }
                        tree001.addParticle(p);
                     }
                     else
                     {
                        Octant northEastLower = this.oct.octant011();
                        if(p.isIn(northEastLower))
                        {
                           if(this.tree011==null)
                           {
                              this.tree011= new Tree(northEastLower);
                           }
                           tree011.addParticle(p);
                        }
                        else
                        {
                           Octant southWestLower = this.oct.octant101();
                           if(p.isIn(southWestLower))
                           {
                              if(this.tree101==null)
                              {
                                 this.tree101= new Tree(southWestLower);
                              }
                              tree101.addParticle(p);
                           }
                           else
                           {
                              Octant southEastLower = this.oct.octant111();
                              if(this.tree111==null)
                              {
                                 this.tree111= new Tree(southEastLower);
                              }
                              tree111.addParticle(p);
                           }
                        }
                     }      
                  }
               }
            }
         }
      }
      else if(this.isNullTree(this))
      {
         Particle c= this.part;
         Octant northWestUpper = this.oct.octant000();
         if (c.isIn(northWestUpper))
         {
            if(this.tree000==null)
            {
               this.tree000= new Tree(northWestUpper);
            }
            tree000.addParticle(c);
         }
         else
         {
            Octant northEastUpper = this.oct.octant010();
            if (c.isIn(northEastUpper))
            {
               if(this.tree010==null)
               {
                  this.tree010= new Tree(northEastUpper);
               }
               tree010.addParticle(c);
            }
            else
            {
               Octant southWestUpper = this.oct.octant100();
               if (c.isIn(southWestUpper))
               {
                  if(this.tree100==null)
                  {
                     this.tree100= new Tree(southWestUpper);
                  }
                  tree100.addParticle(c);
               }
               else
               {
                  Octant southEastUpper = this.oct.octant110();
                  if (c.isIn(southEastUpper))
                  {
                     if(this.tree110==null)
                     {
                        this.tree110= new Tree(southEastUpper);
                     }
                     tree110.addParticle(c);
                  }
                  else
                  {
                     Octant northWestLower = this.oct.octant001();
                     if (c.isIn(northWestLower))
                     {
                        if(this.tree001==null)
                        {
                           this.tree001= new Tree(northWestLower);
                        }
                        tree001.addParticle(c);
                     }
                     else
                     {
                        Octant northEastLower = this.oct.octant011();
                        if (c.isIn(northEastLower))
                        {
                           if(this.tree011==null)
                           {
                              this.tree011= new Tree(northEastLower);
                           }
                           tree011.addParticle(c);
                        }
                        else
                        {
                           Octant southWestLower = this.oct.octant101();
                           if (c.isIn(southWestLower))
                           {
                              if(this.tree101==null)
                              {
                                 this.tree101= new Tree(southWestLower);
                              }
                              tree101.addParticle(c);
                           }
                           else
                           {
                              Octant southEastLower = this.oct.octant111();
                              if(this.tree111==null)
                              {
                                 this.tree111= new Tree(southEastLower);
                              }
                              tree111.addParticle(c);
                           }
                        }
                     } 
                  }
               }
            }
         }
         this.addParticle(p);   
      }
   }//end addParticle method
   
   //recursively go through tree to add acceleration
   public void updateAccel(Particle p)
   {
      if(this.isNullTree(this))
      {
         if(this.part!=p) 
         {
            p.addAcceleration(this.part);
         }
      }
      else if (this.oct.getLength()/(this.part.calcDistance(p))<2.5)
      {
         p.addAcceleration(this.part);
      }
      else
      {
         if(this.tree000!=null)
         {
            this.tree000.updateAccel(p);
         }
         if(this.tree010!=null)
         {
            this.tree010.updateAccel(p);
         }
         if(this.tree100!=null)
         {
            this.tree100.updateAccel(p);
         }
         if(this.tree110!=null)
         {
            this.tree110.updateAccel(p);
         }
         if(this.tree001!=null)
         {
            this.tree001.updateAccel(p);
         }
         if(this.tree011!=null)
         {
            this.tree011.updateAccel(p);
         }
         if(this.tree101!=null)
         {
            this.tree101.updateAccel(p);
         }
         if(this.tree111!=null)
         {
            this.tree111.updateAccel(p);
         }
      }
   }//end update Accel
   
}